package com.app.f2x.application.service;

import com.app.f2x.domain.model.Producto;
import com.app.f2x.domain.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto crearProducto(Producto producto) {
        // Validación: solo cuentas de ahorros o corriente
        if (producto.getTipoCuenta() == null) {
            throw new IllegalArgumentException("Tipo de cuenta requerido");
        }
        // Validación: cuenta de ahorros no puede tener saldo menor a 0
        if (producto.getTipoCuenta() == Producto.TipoCuenta.AHORROS &&
                producto.getSaldo() != null && producto.getSaldo().compareTo(java.math.BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La cuenta de ahorros no puede tener saldo menor a 0");
        }

        // Generación automática del número de cuenta
        String prefijo = producto.getTipoCuenta() == Producto.TipoCuenta.AHORROS ? "53" : "33";
        String numeroCuenta;
        int intentos = 0;
        do {
            numeroCuenta = prefijo + String.format("%08d", (int)(Math.random() * 100_000_000));
            intentos++;
            if (intentos > 10) {
                throw new IllegalStateException("No se pudo generar un número de cuenta único");
            }
        } while (productoRepository.existsByNumeroCuenta(numeroCuenta));
        producto.setNumeroCuenta(numeroCuenta);

        return productoRepository.save(producto);
    }

    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        existente.setEstado(producto.getEstado());
        existente.setSaldo(producto.getSaldo());
        existente.setExentaGmf(producto.getExentaGmf());
        existente.setFechaModificacion(java.time.LocalDateTime.now());
        return productoRepository.save(existente);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public List<Producto> obtenerProductosPorCliente(Long clienteId) {
        return productoRepository.findByClienteId(clienteId);
    }
}
