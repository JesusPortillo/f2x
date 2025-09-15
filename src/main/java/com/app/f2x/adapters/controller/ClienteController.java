package com.app.f2x.adapters.controller;

import com.app.f2x.application.service.ClienteService;
import com.app.f2x.application.service.TransaccionService;
import com.app.f2x.domain.model.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        logger.info("Se recibe cliente para creacion");
        return ResponseEntity.ok(clienteService.crearCliente(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
        logger.info("Se recibe id de cliente para busqueda: "+ id);
        return clienteService.obtenerClientePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        logger.info("Se recibe peticion para listado de clientes");
        return clienteService.listarClientes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        logger.info("Se recibe peticion para actualizar cliente con id: " + id);
        return ResponseEntity.ok(clienteService.actualizarCliente(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        logger.info("Se recibe peticion para eliminar cliente con id: " + id);
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
