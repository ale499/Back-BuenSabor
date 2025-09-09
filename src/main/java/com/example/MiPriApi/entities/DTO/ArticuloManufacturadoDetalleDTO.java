package com.example.MiPriApi.entities.DTO;


import lombok.Data;
import java.util.List;

@Data
public class ArticuloManufacturadoDetalleDTO {
    private Long id;
    private String denominacion;
    private Long categoriaId;
    private CategoriaDTO categoria;
    private List<String> imagenes;
    private Double precioVenta;
    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;
    private List<DetalleDTO> detalles;
    private String type;
    private Boolean descuento;
    private Double precioDescuento;

    @Data
    public static class CategoriaDTO {
        private Long id;
        private String denominacion;
    }

    @Data
    public static class DetalleDTO {
        private String tipo;
        private Integer cantidad;
        private ItemDTO item;

        @Data
        public static class ItemDTO {
            private Long id;
            private String denominacion;
            private Long categoriaId;
            private String unidadMedida;
            private Double precioCompra;
            private Integer stockActual;
        }
    }
}