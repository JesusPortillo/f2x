package com.app.f2x.infrastructure.persistence.adapter;

import com.app.f2x.domain.model.Producto;
import com.app.f2x.domain.repository.ProductoRepository;
import com.app.f2x.infrastructure.persistence.ProductoJpaRepository;
import com.app.f2x.infrastructure.persistence.entity.ProductoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductoRepositoryAdapter implements ProductoRepository {
    private final ProductoJpaRepository productoJpaRepository;

    public ProductoRepositoryAdapter(ProductoJpaRepository productoJpaRepository) {
        this.productoJpaRepository = productoJpaRepository;
    }

    @Override
    public Producto save(Producto producto) {
        ProductoEntity entity = ProductoMapper.toEntity(producto);
        ProductoEntity saved = productoJpaRepository.save(entity);
        return ProductoMapper.toDomain(saved);
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return productoJpaRepository.findById(id).map(ProductoMapper::toDomain);
    }

    @Override
    public Optional<Producto> findByNumeroCuenta(String numeroCuenta) {
        return productoJpaRepository.findByNumeroCuenta(numeroCuenta).map(ProductoMapper::toDomain);
    }

    @Override
    public List<Producto> findAll() {
        return productoJpaRepository.findAll().stream().map(ProductoMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        productoJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByNumeroCuenta(String numeroCuenta) {
        return productoJpaRepository.existsByNumeroCuenta(numeroCuenta);
    }

    @Override
    public List<Producto> findByClienteId(Long clienteId) {
        return productoJpaRepository.findByClienteId(clienteId).stream().map(ProductoMapper::toDomain).collect(Collectors.toList());
    }
}
