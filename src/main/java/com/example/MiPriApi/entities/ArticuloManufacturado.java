package com.example.MiPriApi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "articuloManufacturados")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "detalles")
@EqualsAndHashCode(exclude = "detalles")
@SuperBuilder
public class ArticuloManufacturado extends Articulo{

    private String descripcion;

    private Integer tiempoEstimadoMinutos;

    private String preparacion;

    private Double valorAgregado;


    @OneToMany(mappedBy = "articuloManufacturado", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ArticuloManufacturadoDetalle> detalles = new HashSet<>();


}
