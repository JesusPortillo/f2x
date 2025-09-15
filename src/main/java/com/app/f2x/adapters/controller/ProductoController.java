package com.app.f2x.adapters.controller;

import com.app.f2x.application.service.ProductoService;
import com.app.f2x.domain.model.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        logger.info("Se recibe producto para creacion");
        return ResponseEntity.ok(productoService.crearProducto(producto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        logger.info("Se recibe id de producto para busqueda: "+ id);
        return productoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Producto> listarProductos() {
        logger.info("Se recibe peticion para listado de productos");
        return productoService.listarProductos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        logger.info("Se recibe peticion para actualizar producto con id: " + id);
        return ResponseEntity.ok(productoService.actualizarProducto(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        logger.info("Se recibe peticion para eliminar producto con id: " + id);
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Producto> obtenerProductosPorCliente(@PathVariable Long clienteId) {
        logger.info("Se recibe peticion para obtener productos de cliente: " + clienteId);
        return productoService.obtenerProductosPorCliente(clienteId);
    }
}
