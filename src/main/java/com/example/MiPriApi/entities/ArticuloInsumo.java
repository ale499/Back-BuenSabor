package com.example.MiPriApi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "articulo_insumos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ArticuloInsumo extends Articulo {

    private Double precioCompra;
    private Integer stockActual;
    private Integer stockMaximo;
    private Integer stockMinimo;
    private Boolean esParaElaborar;


    @Builder.Default
    private Integer stockPendiente = 0;

}
