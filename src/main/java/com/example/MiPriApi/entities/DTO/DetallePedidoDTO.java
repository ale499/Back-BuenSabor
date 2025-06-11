package com.example.MiPriApi.entities.DTO;

public class DetallePedidoDTO {
    private int cantidad;
    private double subTotal;
    private ProductoDTO articulo;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public ProductoDTO getArticulo() {
        return articulo;
    }

    public void setArticulo(ProductoDTO articulo) {
        this.articulo = articulo;
    }
}
