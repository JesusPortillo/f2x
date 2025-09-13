package com.app.f2x.infrastructure.persistence.adapter;

import com.app.f2x.domain.model.Transaccion;
import com.app.f2x.infrastructure.persistence.entity.TransaccionEntity;

public class TransaccionMapper {
    public static TransaccionEntity toEntity(Transaccion transaccion) {
        if (transaccion == null) return null;
        return TransaccionEntity.builder()
                .id(transaccion.getId())
                .tipo(toEntityTipoTransaccion(transaccion.getTipo()))
                .valor(transaccion.getValor())
                .fecha(transaccion.getFecha())
                .productoOrigen(ProductoMapper.toEntity(transaccion.getProductoOrigen()))
                .productoDestino(ProductoMapper.toEntity(transaccion.getProductoDestino()))
                .descripcion(transaccion.getDescripcion())
                .build();
    }

    public static Transaccion toDomain(TransaccionEntity entity) {
        if (entity == null) return null;
        return new Transaccion(
                entity.getId(),
                toDomainTipoTransaccion(entity.getTipo()),
                entity.getValor(),
                entity.getFecha(),
                ProductoMapper.toDomain(entity.getProductoOrigen()),
                ProductoMapper.toDomain(entity.getProductoDestino()),
                entity.getDescripcion()
        );
    }

    private static TransaccionEntity.TipoTransaccion toEntityTipoTransaccion(Transaccion.TipoTransaccion tipo) {
        if (tipo == null) return null;
        return TransaccionEntity.TipoTransaccion.valueOf(tipo.name());
    }

    private static Transaccion.TipoTransaccion toDomainTipoTransaccion(TransaccionEntity.TipoTransaccion tipo) {
        if (tipo == null) return null;
        return Transaccion.TipoTransaccion.valueOf(tipo.name());
    }
}
