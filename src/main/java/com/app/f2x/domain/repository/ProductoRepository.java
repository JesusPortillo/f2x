package com.app.f2x.domain.repository;

import com.app.f2x.domain.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    Producto save(Producto producto);
    Optional<Producto> findById(Long id);
    Optional<Producto> findByNumeroCuenta(String numeroCuenta);
    List<Producto> findAll();
    void deleteById(Long id);
    boolean existsByNumeroCuenta(String numeroCuenta);
    List<Producto> findByClienteId(Long clienteId);
}
