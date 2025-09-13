package com.app.f2x.infrastructure.persistence;

import com.app.f2x.infrastructure.persistence.entity.TransaccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransaccionJpaRepository extends JpaRepository<TransaccionEntity, Long> {
    List<TransaccionEntity> findByProductoOrigenId(Long productoId);
    List<TransaccionEntity> findByProductoDestinoId(Long productoId);
}
