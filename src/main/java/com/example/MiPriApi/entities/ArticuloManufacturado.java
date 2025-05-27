package com.example.MiPriApi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articuloManufacturados")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticuloManufacturado extends Articulo{

    private String descripcion;

    private Integer tiempoEstimadoMinutos;

    private String preparacion;




}
