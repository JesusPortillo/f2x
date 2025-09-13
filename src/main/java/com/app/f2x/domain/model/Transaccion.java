package com.app.f2x.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaccion {
    private Long id;
    private TipoTransaccion tipo;
    private BigDecimal valor;
    private LocalDateTime fecha;
    private Producto productoOrigen;
    private Producto productoDestino;
    private String descripcion;

    public Transaccion() {}

    public Transaccion(Long id, TipoTransaccion tipo, BigDecimal valor, LocalDateTime fecha, Producto productoOrigen, Producto productoDestino, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.fecha = fecha;
        this.productoOrigen = productoOrigen;
        this.productoDestino = productoDestino;
        this.descripcion = descripcion;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TipoTransaccion getTipo() { return tipo; }
    public void setTipo(TipoTransaccion tipo) { this.tipo = tipo; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public Producto getProductoOrigen() { return productoOrigen; }
    public void setProductoOrigen(Producto productoOrigen) { this.productoOrigen = productoOrigen; }
    public Producto getProductoDestino() { return productoDestino; }
    public void setProductoDestino(Producto productoDestino) { this.productoDestino = productoDestino; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public enum TipoTransaccion {
        CONSIGNACION, RETIRO, TRANSFERENCIA
    }
}
