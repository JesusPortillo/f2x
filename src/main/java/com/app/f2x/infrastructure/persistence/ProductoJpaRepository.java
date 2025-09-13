package com.app.f2x.infrastructure.persistence;

import com.app.f2x.infrastructure.persistence.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductoJpaRepository extends JpaRepository<ProductoEntity, Long> {
    Optional<ProductoEntity> findByNumeroCuenta(String numeroCuenta);
    boolean existsByNumeroCuenta(String numeroCuenta);
    List<ProductoEntity> findByClienteId(Long clienteId);
}
