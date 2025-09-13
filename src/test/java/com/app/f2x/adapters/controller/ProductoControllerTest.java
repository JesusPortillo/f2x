package com.app.f2x.adapters.controller;

import com.app.f2x.application.service.ProductoService;
import com.app.f2x.domain.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoControllerTest {
    @Mock
    private ProductoService productoService;
    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerProducto_existente_retornaOk() {
        Producto producto = new Producto();
        producto.setId(1L);
        when(productoService.obtenerProductoPorId(1L)).thenReturn(Optional.of(producto));
        ResponseEntity<Producto> response = productoController.obtenerProducto(1L);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(producto, response.getBody());
    }

    @Test
    void obtenerProducto_noExistente_retornaNotFound() {
        when(productoService.obtenerProductoPorId(2L)).thenReturn(Optional.empty());
        ResponseEntity<Producto> response = productoController.obtenerProducto(2L);
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }
}