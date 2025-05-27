package com.example.MiPriApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Empleado extends Persona {

    @ManyToOne
    @JoinColumn(name = "sucursalId")
    private Sucursal sucursal;

    @OneToOne
    @JoinColumn(name = "usuarioEmpleado")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "imagenEmpleado")
    private Imagen imagen;
}
