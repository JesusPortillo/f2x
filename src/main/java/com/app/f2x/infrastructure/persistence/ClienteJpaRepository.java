package com.app.f2x.infrastructure.persistence;

import com.app.f2x.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByNumeroIdentificacion(String numeroIdentificacion);
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
}
