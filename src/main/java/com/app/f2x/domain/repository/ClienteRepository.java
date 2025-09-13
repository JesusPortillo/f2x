package com.app.f2x.domain.repository;

import com.app.f2x.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    Optional<Cliente> findByNumeroIdentificacion(String numeroIdentificacion);
    List<Cliente> findAll();
    void deleteById(Long id);
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
    boolean existsById(Long id);
}
