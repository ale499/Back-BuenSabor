package com.example.MiPriApi.entities;

import com.example.MiPriApi.entities.enums.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Administrador extends Base{

    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;

    @OneToOne
    @JoinColumn(name = "usuarioAdmin")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "imagenAdmin")
<<<<<<< HEAD
    private Imagen imagen;
=======
    private Image imagen;
>>>>>>> Dev


}
