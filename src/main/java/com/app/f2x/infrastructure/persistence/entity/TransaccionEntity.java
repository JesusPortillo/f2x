package com.app.f2x.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransaccion tipo;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_origen_id")
    private ProductoEntity productoOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_destino_id")
    private ProductoEntity productoDestino;

    private String descripcion;

    public enum TipoTransaccion {
        CONSIGNACION, RETIRO, TRANSFERENCIA
    }
}
