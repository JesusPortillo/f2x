package com.app.f2x.application.service;

import com.app.f2x.domain.model.Transaccion;
import com.app.f2x.domain.model.Producto;
import com.app.f2x.domain.repository.TransaccionRepository;
import com.app.f2x.domain.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransaccionService {
    private final TransaccionRepository transaccionRepository;
    private final ProductoRepository productoRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransaccionService.class);


    public TransaccionService(TransaccionRepository transaccionRepository, ProductoRepository productoRepository) {
        this.transaccionRepository = transaccionRepository;
        this.productoRepository = productoRepository;
    }

    public Transaccion crearTransaccion(Transaccion transaccion) {
        logger.info("Creando transaccion");
        logger.info("Iniciando validaciones");
        if (transaccion.getTipo() == null) {
            logger.error("Tipo de transacción requerido");
            throw new IllegalArgumentException("Tipo de transacción requerido");
        }
        if (transaccion.getValor() == null || transaccion.getValor().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            logger.error("El valor debe ser mayor a 0");
            throw new IllegalArgumentException("El valor debe ser mayor a 0");
        }

        // Asignar fecha si no viene en el request
        if (transaccion.getFecha() == null) {
            logger.info("Asignando fecha");
            transaccion.setFecha(java.time.LocalDateTime.now());
        }
        switch (transaccion.getTipo()) {
            case CONSIGNACION -> {
                if (transaccion.getProductoDestino() == null || transaccion.getProductoDestino().getId() == null) {
                    logger.error("Debe especificar la cuenta destino");
                    throw new IllegalArgumentException("Debe especificar la cuenta destino");
                }
                Producto destino = productoRepository.findById(transaccion.getProductoDestino().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Cuenta destino no encontrada"));
                destino.setSaldo(destino.getSaldo().add(transaccion.getValor()));
                destino.setFechaModificacion(java.time.LocalDateTime.now());
                productoRepository.save(destino);
            }
            case RETIRO -> {
                if (transaccion.getProductoOrigen() == null || transaccion.getProductoOrigen().getId() == null) {
                    logger.error("Debe especificar la cuenta origen");
                    throw new IllegalArgumentException("Debe especificar la cuenta origen");
                }
                Producto origen = productoRepository.findById(transaccion.getProductoOrigen().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no encontrada"));
                if (origen.getSaldo().compareTo(transaccion.getValor()) < 0) {
                    logger.error("Saldo insuficiente para el retiro");
                    throw new IllegalArgumentException("Saldo insuficiente para el retiro");
                }
                origen.setSaldo(origen.getSaldo().subtract(transaccion.getValor()));
                origen.setFechaModificacion(java.time.LocalDateTime.now());
                productoRepository.save(origen);
            }
            case TRANSFERENCIA -> {
                // Validar cuentas origen y destino
                if (transaccion.getProductoOrigen() == null || transaccion.getProductoOrigen().getId() == null) {
                    logger.error("Debe especificar la cuenta origen");
                    throw new IllegalArgumentException("Debe especificar la cuenta origen");
                }
                if (transaccion.getProductoDestino() == null || transaccion.getProductoDestino().getId() == null) {
                    logger.error("Debe especificar la cuenta destino");
                    throw new IllegalArgumentException("Debe especificar la cuenta destino");
                }
                if (transaccion.getProductoOrigen().getId().equals(transaccion.getProductoDestino().getId())) {
                    logger.error("La cuenta origen y destino deben ser diferentes");
                    throw new IllegalArgumentException("La cuenta origen y destino deben ser diferentes");
                }
                Producto origen = productoRepository.findById(transaccion.getProductoOrigen().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no encontrada"));
                Producto destino = productoRepository.findById(transaccion.getProductoDestino().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Cuenta destino no encontrada"));
                if (origen.getSaldo().compareTo(transaccion.getValor()) < 0) {
                    logger.error("Saldo insuficiente para la transferencia");
                    throw new IllegalArgumentException("Saldo insuficiente para la transferencia");
                }
                // Debitar origen
                origen.setSaldo(origen.getSaldo().subtract(transaccion.getValor()));
                origen.setFechaModificacion(java.time.LocalDateTime.now());
                productoRepository.save(origen);
                // Acreditar destino
                destino.setSaldo(destino.getSaldo().add(transaccion.getValor()));
                destino.setFechaModificacion(java.time.LocalDateTime.now());
                productoRepository.save(destino);
                // Guardar movimiento de débito (transacción original)
                if (transaccion.getFecha() == null) {
                    transaccion.setFecha(java.time.LocalDateTime.now());
                }
                transaccionRepository.save(transaccion);
                // Guardar movimiento de crédito (transacción espejo)
                Transaccion transaccionCredito = new Transaccion();
                transaccionCredito.setTipo(Transaccion.TipoTransaccion.TRANSFERENCIA);
                transaccionCredito.setValor(transaccion.getValor());
                transaccionCredito.setFecha(java.time.LocalDateTime.now());
                transaccionCredito.setProductoOrigen(transaccion.getProductoOrigen());
                transaccionCredito.setProductoDestino(transaccion.getProductoDestino());
                transaccionCredito.setDescripcion("Transferencia recibida de cuenta " + origen.getNumeroCuenta());
                transaccionRepository.save(transaccionCredito);
                return transaccion;
            }
        }
        logger.info("Guardando transaccion");
        return transaccionRepository.save(transaccion);
    }

    public Optional<Transaccion> obtenerTransaccionPorId(Long id) {
        logger.info("Obteniendo transaccion por id: " + id);
        return transaccionRepository.findById(id);
    }

    public List<Transaccion> listarTransaccionesPorProducto(Long productoId) {
        logger.info("Listando transacciones por producto con id: " + productoId);
        return transaccionRepository.findByProductoId(productoId);
    }

    public List<Transaccion> listarTransacciones() {
        logger.info("Listando transacciones");
        return transaccionRepository.findAll();
    }
}
