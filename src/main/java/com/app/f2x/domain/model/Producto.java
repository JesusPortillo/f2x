package com.app.f2x.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Producto {
    private Long id;
    private TipoCuenta tipoCuenta;
    private String numeroCuenta;
    private EstadoCuenta estado;
    private BigDecimal saldo;
    private Boolean exentaGmf;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Cliente cliente;

    public Producto() {}

    public Producto(Long id, TipoCuenta tipoCuenta, String numeroCuenta, EstadoCuenta estado, BigDecimal saldo, Boolean exentaGmf, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion, Cliente cliente) {
        this.id = id;
        this.tipoCuenta = tipoCuenta;
        this.numeroCuenta = numeroCuenta;
        this.estado = estado;
        this.saldo = saldo;
        this.exentaGmf = exentaGmf;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.cliente = cliente;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(TipoCuenta tipoCuenta) { this.tipoCuenta = tipoCuenta; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    public EstadoCuenta getEstado() { return estado; }
    public void setEstado(EstadoCuenta estado) { this.estado = estado; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    public Boolean getExentaGmf() { return exentaGmf; }
    public void setExentaGmf(Boolean exentaGmf) { this.exentaGmf = exentaGmf; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public enum TipoCuenta {
        AHORROS, CORRIENTE
    }

    public enum EstadoCuenta {
        ACTIVA, INACTIVA, CANCELADA
    }
}
