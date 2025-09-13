package com.app.f2x.domain.repository;

import com.app.f2x.domain.model.Transaccion;
import java.util.List;
import java.util.Optional;

public interface TransaccionRepository {
    Transaccion save(Transaccion transaccion);
    Optional<Transaccion> findById(Long id);
    List<Transaccion> findByProductoId(Long productoId);
    List<Transaccion> findAll();
}
