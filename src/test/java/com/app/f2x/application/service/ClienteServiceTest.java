package com.app.f2x.application.service;

import com.app.f2x.domain.model.Cliente;
import com.app.f2x.domain.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearCliente_menorDeEdad_lanzaExcepcion() {
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(LocalDate.now().minusYears(17));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> clienteService.crearCliente(cliente));
        assertTrue(ex.getMessage().contains("mayor de edad"));
    }

    @Test
    void crearCliente_duplicado_lanzaExcepcion() {
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(LocalDate.now().minusYears(20));
        cliente.setNumeroIdentificacion("123");
        when(clienteRepository.existsByNumeroIdentificacion("123")).thenReturn(true);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> clienteService.crearCliente(cliente));
        assertTrue(ex.getMessage().contains("Ya existe un cliente"));
    }

    @Test
    void crearCliente_valido_retornaCliente() {
        Cliente cliente = new Cliente();
        cliente.setFechaNacimiento(LocalDate.now().minusYears(25));
        cliente.setNumeroIdentificacion("456");
        when(clienteRepository.existsByNumeroIdentificacion("456")).thenReturn(false);
        when(clienteRepository.save(any())).thenReturn(cliente);
        Cliente result = clienteService.crearCliente(cliente);
        assertEquals("456", result.getNumeroIdentificacion());
    }
}