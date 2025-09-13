package com.app.f2x.application.service;

import com.app.f2x.domain.model.Cliente;
import com.app.f2x.domain.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente crearCliente(Cliente cliente) {
        // Validación de mayor de edad
        if (cliente.getFechaNacimiento() != null &&
                cliente.getFechaNacimiento().plusYears(18).isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("El cliente debe ser mayor de edad");
        }
        // Validación de unicidad
        if (clienteRepository.existsByNumeroIdentificacion(cliente.getNumeroIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un cliente con ese número de identificación");
        }
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente actualizarCliente(Long id, Cliente cliente) {
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
        clienteRepository.deleteById(id);
    }
}
