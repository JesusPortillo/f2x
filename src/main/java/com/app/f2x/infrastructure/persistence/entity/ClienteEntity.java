package com.app.f2x.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tipo_identificacion", nullable = false)
    private String tipoIdentificacion;

    @NotNull
    @Column(name = "numero_identificacion", nullable = false, unique = true)
    private String numeroIdentificacion;

    @NotNull
    @Size(min = 2)
    @Column(nullable = false)
    private String nombres;

    @NotNull
    @Size(min = 2)
    @Column(nullable = false)
    private String apellido;

    @NotNull
    @Email
    @Column(nullable = false, unique = true)
    private String correoElectronico;

    @NotNull
    @Past
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }
}
