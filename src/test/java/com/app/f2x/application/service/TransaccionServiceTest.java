package com.app.f2x.application.service;

import com.app.f2x.application.service.TransaccionService;
import com.app.f2x.domain.model.Producto;
import com.app.f2x.domain.model.Transaccion;
import com.app.f2x.domain.repository.ProductoRepository;
import com.app.f2x.domain.repository.TransaccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransaccionServiceTest {
    @Mock
    private TransaccionRepository transaccionRepository;
    @Mock
    private ProductoRepository productoRepository;
    @InjectMocks
    private TransaccionService transaccionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearTransaccion_retiro_saldoInsuficiente_lanzaExcepcion() {
        Producto origen = new Producto();
        origen.setId(1L);
        origen.setSaldo(new BigDecimal("50"));
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(Transaccion.TipoTransaccion.RETIRO);
        transaccion.setValor(new BigDecimal("100"));
        transaccion.setProductoOrigen(origen);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(origen));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> transaccionService.crearTransaccion(transaccion));
        assertTrue(ex.getMessage().contains("Saldo insuficiente"));
    }

    @Test
    void crearTransaccion_consignacion_actualizaSaldo() {
        Producto destino = new Producto();
        destino.setId(2L);
        destino.setSaldo(new BigDecimal("100"));
        Transaccion transaccion = new Transaccion();
        transaccion.setTipo(Transaccion.TipoTransaccion.CONSIGNACION);
        transaccion.setValor(new BigDecimal("50"));
        transaccion.setProductoDestino(destino);
        when(productoRepository.findById(2L)).thenReturn(Optional.of(destino));
        when(productoRepository.save(any())).thenReturn(destino);
        when(transaccionRepository.save(any())).thenReturn(transaccion);
        Transaccion result = transaccionService.crearTransaccion(transaccion);
        assertEquals(new BigDecimal("150"), destino.getSaldo());
        assertEquals(transaccion, result);
    }
}