package com.app.f2x.adapters.controller;

import com.app.f2x.application.service.TransaccionService;
import com.app.f2x.domain.model.Transaccion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {
    private final TransaccionService transaccionService;
    private static final Logger logger = LoggerFactory.getLogger(TransaccionController.class);

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping
    public ResponseEntity<Transaccion> crearTransaccion(@RequestBody Transaccion transaccion) {
        logger.info("Se recibe transaccion para creacion");
        return ResponseEntity.ok(transaccionService.crearTransaccion(transaccion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> obtenerTransaccion(@PathVariable Long id) {
        logger.info("Se recibe id de transaccion para busqueda: "+ id);
        return transaccionService.obtenerTransaccionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Transaccion> listarTransacciones() {
        logger.info("Se recibe peticion para listado de transacciones");
        return transaccionService.listarTransacciones();
    }

    @GetMapping("/producto/{productoId}")
    public List<Transaccion> listarTransaccionesPorProducto(@PathVariable Long productoId) {
        logger.info("Se recibe peticion para obtener transacciones de producto: " + productoId);
        return transaccionService.listarTransaccionesPorProducto(productoId);
    }
}
