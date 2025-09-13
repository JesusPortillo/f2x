package com.app.f2x.adapters.controller;

import com.app.f2x.application.service.TransaccionService;
import com.app.f2x.domain.model.Transaccion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {
    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping
    public ResponseEntity<Transaccion> crearTransaccion(@RequestBody Transaccion transaccion) {
        return ResponseEntity.ok(transaccionService.crearTransaccion(transaccion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> obtenerTransaccion(@PathVariable Long id) {
        return transaccionService.obtenerTransaccionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Transaccion> listarTransacciones() {
        return transaccionService.listarTransacciones();
    }

    @GetMapping("/producto/{productoId}")
    public List<Transaccion> listarTransaccionesPorProducto(@PathVariable Long productoId) {
        return transaccionService.listarTransaccionesPorProducto(productoId);
    }
}
