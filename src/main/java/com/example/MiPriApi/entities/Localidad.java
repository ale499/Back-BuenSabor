package com.example.MiPriApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "localidades")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Localidad extends Base{

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "provinciaId")
    private Provincia provincia;
}
