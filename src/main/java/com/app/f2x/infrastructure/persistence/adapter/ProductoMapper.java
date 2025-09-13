package com.app.f2x.infrastructure.persistence.adapter;

import com.app.f2x.domain.model.Producto;
import com.app.f2x.infrastructure.persistence.entity.ProductoEntity;

public class ProductoMapper {
    public static ProductoEntity toEntity(Producto producto) {
        if (producto == null) return null;
        return ProductoEntity.builder()
                .id(producto.getId())
                .tipoCuenta(toEntityTipoCuenta(producto.getTipoCuenta()))
                .numeroCuenta(producto.getNumeroCuenta())
                .estado(toEntityEstadoCuenta(producto.getEstado()))
                .saldo(producto.getSaldo())
                .exentaGmf(producto.getExentaGmf())
                .fechaCreacion(producto.getFechaCreacion())
                .fechaModificacion(producto.getFechaModificacion())
                .cliente(ClienteMapper.toEntity(producto.getCliente()))
                .build();
    }

    public static Producto toDomain(ProductoEntity entity) {
        if (entity == null) return null;
        return new Producto(
                entity.getId(),
                toDomainTipoCuenta(entity.getTipoCuenta()),
                entity.getNumeroCuenta(),
                toDomainEstadoCuenta(entity.getEstado()),
                entity.getSaldo(),
                entity.getExentaGmf(),
                entity.getFechaCreacion(),
                entity.getFechaModificacion(),
                ClienteMapper.toDomain(entity.getCliente())
        );
    }

    private static ProductoEntity.TipoCuenta toEntityTipoCuenta(Producto.TipoCuenta tipoCuenta) {
        if (tipoCuenta == null) return null;
        return ProductoEntity.TipoCuenta.valueOf(tipoCuenta.name());
    }

    private static Producto.TipoCuenta toDomainTipoCuenta(ProductoEntity.TipoCuenta tipoCuenta) {
        if (tipoCuenta == null) return null;
        return Producto.TipoCuenta.valueOf(tipoCuenta.name());
    }

    private static ProductoEntity.EstadoCuenta toEntityEstadoCuenta(Producto.EstadoCuenta estadoCuenta) {
        if (estadoCuenta == null) return null;
        return ProductoEntity.EstadoCuenta.valueOf(estadoCuenta.name());
    }

    private static Producto.EstadoCuenta toDomainEstadoCuenta(ProductoEntity.EstadoCuenta estadoCuenta) {
        if (estadoCuenta == null) return null;
        return Producto.EstadoCuenta.valueOf(estadoCuenta.name());
    }
}
