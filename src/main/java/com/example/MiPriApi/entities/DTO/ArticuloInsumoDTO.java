package com.example.MiPriApi.entities.DTO;

import lombok.Data;

@Data
public class ArticuloInsumoDTO {
    private Long id;
    private String denominacion;
    private CategoriaDTO categoria;
    private String unidadMedida;
    private double precioVenta;
    private double precioCompra;
    private int stockActual;
    private int stockMaximo;
    private int stockMinimo;
    private boolean esParaElaborar;

}
