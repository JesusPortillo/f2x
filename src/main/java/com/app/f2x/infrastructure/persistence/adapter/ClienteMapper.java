package com.app.f2x.infrastructure.persistence.adapter;

import com.app.f2x.domain.model.Cliente;
import com.app.f2x.infrastructure.persistence.entity.ClienteEntity;

public class ClienteMapper {
    public static ClienteEntity toEntity(Cliente cliente) {
        if (cliente == null) return null;
        return ClienteEntity.builder()
                .id(cliente.getId())
                .tipoIdentificacion(cliente.getTipoIdentificacion())
                .numeroIdentificacion(cliente.getNumeroIdentificacion())
                .nombres(cliente.getNombres())
                .apellido(cliente.getApellido())
                .correoElectronico(cliente.getCorreoElectronico())
                .fechaNacimiento(cliente.getFechaNacimiento())
                .fechaCreacion(cliente.getFechaCreacion())
                .fechaModificacion(cliente.getFechaModificacion())
                .build();
    }

    public static Cliente toDomain(ClienteEntity entity) {
        if (entity == null) return null;
        return new Cliente(
                entity.getId(),
                entity.getTipoIdentificacion(),
                entity.getNumeroIdentificacion(),
                entity.getNombres(),
                entity.getApellido(),
                entity.getCorreoElectronico(),
                entity.getFechaNacimiento(),
                entity.getFechaCreacion(),
                entity.getFechaModificacion()
        );
    }
}
