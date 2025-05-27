package com.example.MiPriApi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "articuloInsumos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticuloInsumo extends Articulo {

    private String denominacion;
    private Double precioCompra;
    private Double precioVenta;
    private Integer stockActual;
    private Integer stockMaximo;
    private Integer stockMinimo;
    private Boolean esParaElaborar;

}
