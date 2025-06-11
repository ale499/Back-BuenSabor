package com.example.MiPriApi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
=======
import lombok.experimental.SuperBuilder;
>>>>>>> Dev


@Entity
@Table(name = "articuloInsumos")
@Data
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD
@Builder
public class ArticuloInsumo extends Articulo {

    private Double precioCompra;
    private Double precioVenta;
=======
@SuperBuilder
public class ArticuloInsumo extends Articulo {

    private Double precioCompra;
>>>>>>> Dev
    private Integer stockActual;
    private Integer stockMaximo;
    private Integer stockMinimo;
    private Boolean esParaElaborar;

}
