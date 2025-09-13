package com.app.f2x.adapters.controller;

import com.app.f2x.application.service.ClienteService;
import com.app.f2x.domain.model.Cliente;
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

class ClienteControllerTest {
    @Mock
    private ClienteService clienteService;
    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerCliente_existente_retornaOk() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteService.obtenerClientePorId(1L)).thenReturn(Optional.of(cliente));
        ResponseEntity<Cliente> response = clienteController.obtenerCliente(1L);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(cliente, response.getBody());
    }

    @Test
    void obtenerCliente_noExistente_retornaNotFound() {
        when(clienteService.obtenerClientePorId(2L)).thenReturn(Optional.empty());
        ResponseEntity<Cliente> response = clienteController.obtenerCliente(2L);
        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }
}