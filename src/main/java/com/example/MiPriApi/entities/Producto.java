package com.example.MiPriApi.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto extends Base {
    private String nombre;
    private String descripcion;
    private Double precio;
    // Agrega otros campos necesarios, como stock, categoría, etc.
}