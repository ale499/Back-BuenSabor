package com.example.MiPriApi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "articuloManufacturados")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ArticuloManufacturado extends Articulo{

    private String descripcion;

    private Integer tiempoEstimadoMinutos;

    private String preparacion;




}
