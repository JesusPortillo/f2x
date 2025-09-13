package com.app.f2x.infrastructure.persistence.adapter;

import com.app.f2x.domain.model.Transaccion;
import com.app.f2x.domain.repository.TransaccionRepository;
import com.app.f2x.infrastructure.persistence.TransaccionJpaRepository;
import com.app.f2x.infrastructure.persistence.entity.TransaccionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TransaccionRepositoryAdapter implements TransaccionRepository {
    private final TransaccionJpaRepository transaccionJpaRepository;

    public TransaccionRepositoryAdapter(TransaccionJpaRepository transaccionJpaRepository) {
        this.transaccionJpaRepository = transaccionJpaRepository;
    }

    @Override
    public Transaccion save(Transaccion transaccion) {
        TransaccionEntity entity = TransaccionMapper.toEntity(transaccion);
        TransaccionEntity saved = transaccionJpaRepository.save(entity);
        return TransaccionMapper.toDomain(saved);
    }

    @Override
    public Optional<Transaccion> findById(Long id) {
        return transaccionJpaRepository.findById(id).map(TransaccionMapper::toDomain);
    }

    @Override
    public List<Transaccion> findByProductoId(Long productoId) {
        List<TransaccionEntity> origen = transaccionJpaRepository.findByProductoOrigenId(productoId);
        List<TransaccionEntity> destino = transaccionJpaRepository.findByProductoDestinoId(productoId);
        return java.util.stream.Stream.concat(origen.stream(), destino.stream())
                .map(TransaccionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaccion> findAll() {
        return transaccionJpaRepository.findAll().stream().map(TransaccionMapper::toDomain).collect(Collectors.toList());
    }
}
