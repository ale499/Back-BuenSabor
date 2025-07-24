package com.example.MiPriApi.entities.DTO;

import java.math.BigDecimal;

public class ItemDTO {
    private String nombre;
    private int cantidad;
    private BigDecimal precio;

    public ItemDTO() {}

    public ItemDTO(String nombre, int cantidad, BigDecimal precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
}
