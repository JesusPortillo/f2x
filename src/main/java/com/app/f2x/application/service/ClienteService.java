package com.app.f2x.application.service;

import com.app.f2x.domain.model.Cliente;
import com.app.f2x.domain.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente crearCliente(Cliente cliente) {
        logger.info("Creando cliente");
        logger.info("Iniciando validaciones correspondientes");
        // Validación de mayor de edad
        if (cliente.getFechaNacimiento() != null &&
                cliente.getFechaNacimiento().plusYears(18).isAfter(java.time.LocalDate.now())) {
            logger.error("El cliente debe ser mayor de edad");
            throw new IllegalArgumentException("El cliente debe ser mayor de edad");
        }
        // Validación de unicidad
        if (clienteRepository.existsByNumeroIdentificacion(cliente.getNumeroIdentificacion())) {
            logger.error("Ya existe un cliente con ese número de identificación");
            throw new IllegalArgumentException("Ya existe un cliente con ese número de identificación");
        }
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> obtenerClientePorId(Long id) {
        logger.info("Buscando cliente por ID");
        return clienteRepository.findById(id);
    }

    public List<Cliente> listarClientes() {
        logger.info("Buscando clientes");
        return clienteRepository.findAll();
    }

    public Cliente actualizarCliente(Long id, Cliente cliente) {
        logger.info("Actualizando cliente de ID: " + id);
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        // Actualizar campos permitidos
        existente.setTipoIdentificacion(cliente.getTipoIdentificacion());
        existente.setNombres(cliente.getNombres());
        existente.setApellido(cliente.getApellido());
        existente.setCorreoElectronico(cliente.getCorreoElectronico());
        existente.setFechaNacimiento(cliente.getFechaNacimiento());
        existente.setFechaModificacion(java.time.LocalDateTime.now());
        return clienteRepository.save(existente);
    }

    public void eliminarCliente(Long id) {
        logger.info("Eliminando cliente de ID: " + id);
        clienteRepository.deleteById(id);
    }
}
