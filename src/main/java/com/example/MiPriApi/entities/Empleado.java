package com.example.MiPriApi.entities;

import com.example.MiPriApi.entities.enums.Rol;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Empleado extends Base{

    private String nombre;
    private String apellido;
    private Rol rol;

    @OneToOne
    @JoinColumn(name = "usuarioEmpleado")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "imagenEmpleado")
    private Image imagen;
}