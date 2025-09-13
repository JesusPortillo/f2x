package com.app.f2x.application.service;

import com.app.f2x.domain.model.Producto;
import com.app.f2x.domain.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;
    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearProducto_ahorrosSaldoNegativo_lanzaExcepcion() {
        Producto producto = new Producto();
        producto.setTipoCuenta(Producto.TipoCuenta.AHORROS);
        producto.setSaldo(new BigDecimal("-10"));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> productoService.crearProducto(producto));
        assertTrue(ex.getMessage().contains("saldo menor a 0"));
    }

    @Test
    void crearProducto_valido_retornaProducto() {
        Producto producto = new Producto();
        producto.setTipoCuenta(Producto.TipoCuenta.AHORROS);
        producto.setSaldo(new BigDecimal("100"));
        when(productoRepository.existsByNumeroCuenta(anyString())).thenReturn(false);
        when(productoRepository.save(any())).thenReturn(producto);
        Producto result = productoService.crearProducto(producto);
        assertEquals(new BigDecimal("100"), result.getSaldo());
    }
}