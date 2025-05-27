package com.example.MiPriApi.entities;

import com.example.MiPriApi.entities.enums.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Persona extends Base implements Serializable{

    protected String nombre;
    protected String apellido;
    protected String telefono;
    protected String email;
    protected String fechaNacimiento;

    protected Rol rol;

    @OneToOne
    @JoinColumn(name = "imagenId")
    protected Imagen imagenPersona;

    @OneToOne
    @JoinColumn(name = "usuarioId")
    protected Usuario usuario;



}
