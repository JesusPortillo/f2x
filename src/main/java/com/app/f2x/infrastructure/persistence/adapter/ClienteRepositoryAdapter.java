package com.app.f2x.infrastructure.persistence.adapter;

import com.app.f2x.domain.model.Cliente;
import com.app.f2x.domain.repository.ClienteRepository;
import com.app.f2x.infrastructure.persistence.ClienteJpaRepository;
import com.app.f2x.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ClienteRepositoryAdapter implements ClienteRepository {
    private final ClienteJpaRepository clienteJpaRepository;

    public ClienteRepositoryAdapter(ClienteJpaRepository clienteJpaRepository) {
        this.clienteJpaRepository = clienteJpaRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = ClienteMapper.toEntity(cliente);
        ClienteEntity saved = clienteJpaRepository.save(entity);
        return ClienteMapper.toDomain(saved);
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return clienteJpaRepository.findById(id).map(ClienteMapper::toDomain);
    }

    @Override
    public Optional<Cliente> findByNumeroIdentificacion(String numeroIdentificacion) {
        return clienteJpaRepository.findByNumeroIdentificacion(numeroIdentificacion).map(ClienteMapper::toDomain);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteJpaRepository.findAll().stream().map(ClienteMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        clienteJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByNumeroIdentificacion(String numeroIdentificacion) {
        return clienteJpaRepository.existsByNumeroIdentificacion(numeroIdentificacion);
    }

    @Override
    public boolean existsById(Long id) {
        return clienteJpaRepository.existsById(id);
    }
}
