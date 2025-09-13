package com.app.f2x.adapters.controller;

import com.app.f2x.application.service.TransaccionService;
import com.app.f2x.domain.model.Transaccion;
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

class TransaccionControllerTest {
    @Mock
    private TransaccionService transaccionService;
    @InjectMocks
    private TransaccionController transaccionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTransaccion_existente_retornaOk() {
        Transaccion transaccion = new Transaccion();
        transaccion.setId(1L);
        when(transaccionService.obtenerTransaccionPorId(1L)).thenReturn(Optional.of(transaccion));
        ResponseEntity<Transaccion> response = transaccionController.obtenerTransaccion(1L);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(transaccion, response.getBody());
    }

    @Test
    void obtenerTransaccion_noExistente_retornaNotFound() {
        when(transaccionService.obtenerTransaccionPorId(2L)).thenReturn(Optional.empty());
        ResponseEntity<Transaccion> response = transaccionController.obtenerTransaccion(2L);
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }

    @Test
    void crearTransaccion_transferencia_entreCuentas() {
        Transaccion transferencia = new Transaccion();
        transferencia.setId(10L);
        transferencia.setTipo(Transaccion.TipoTransaccion.TRANSFERENCIA);
        when(transaccionService.crearTransaccion(any(Transaccion.class))).thenReturn(transferencia);
        ResponseEntity<Transaccion> response = transaccionController.crearTransaccion(transferencia);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(transferencia, response.getBody());
        verify(transaccionService).crearTransaccion(transferencia);
    }
}