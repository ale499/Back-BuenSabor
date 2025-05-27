package com.example.MiPriApi.entities;

import com.example.MiPriApi.entities.enums.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chef extends Base{

    private String nombre;
    private String apellido;
    private Rol rol;

    @OneToOne
    @JoinColumn(name = "usuarioChef")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "imagenChef")
    private Imagen imagen;
}
