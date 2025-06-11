package com.example.MiPriApi.services.Mappers;


import com.example.MiPriApi.entities.ArticuloManufacturadoDetalle;
import com.example.MiPriApi.entities.DTO.ArticuloManufacturadoDetalleDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticuloManufacturadoDetalleMapper {

    public ArticuloManufacturadoDetalleDTO toDTO(ArticuloManufacturadoDetalle detalle) {
        ArticuloManufacturadoDetalleDTO dto = new ArticuloManufacturadoDetalleDTO();
        dto.setId(detalle.getArticuloManufacturado().getId());
        dto.setDenominacion(detalle.getArticuloManufacturado().getDenominacion());
        dto.setCategoriaId(detalle.getArticuloManufacturado().getCategoria().getId());

        ArticuloManufacturadoDetalleDTO.CategoriaDTO categoriaDTO = new ArticuloManufacturadoDetalleDTO.CategoriaDTO();
        categoriaDTO.setId(detalle.getArticuloManufacturado().getCategoria().getId());
        categoriaDTO.setDenominacion(detalle.getArticuloManufacturado().getCategoria().getDenominacion());
        dto.setCategoria(categoriaDTO);

        dto.setImagenes(detalle.getArticuloManufacturado().getImagenesArticulos().stream()
                .map(image -> image.getUrl())
                .collect(Collectors.toList()));
        dto.setPrecioVenta(detalle.getArticuloManufacturado().getPrecioVenta());
        dto.setDescripcion(detalle.getArticuloManufacturado().getDescripcion());
        dto.setTiempoEstimadoMinutos(detalle.getArticuloManufacturado().getTiempoEstimadoMinutos());
        dto.setPreparacion(detalle.getArticuloManufacturado().getPreparacion());

        // Mapear los detalles del artículo manufacturado
        List<ArticuloManufacturadoDetalleDTO.DetalleDTO> detalles = detalle.getArticuloManufacturado().getDetalles().stream().map(d -> {
            ArticuloManufacturadoDetalleDTO.DetalleDTO detalleDTO = new ArticuloManufacturadoDetalleDTO.DetalleDTO();
            detalleDTO.setTipo("INSUMO");
            detalleDTO.setCantidad(d.getCantidad());

            ArticuloManufacturadoDetalleDTO.DetalleDTO.ItemDTO itemDTO = new ArticuloManufacturadoDetalleDTO.DetalleDTO.ItemDTO();
            itemDTO.setId(d.getArticuloInsumo().getId());
            itemDTO.setDenominacion(d.getArticuloInsumo().getDenominacion());
            itemDTO.setCategoriaId(d.getArticuloInsumo().getCategoria().getId());
            itemDTO.setUnidadMedida(d.getArticuloInsumo().getUnidadMedida().getDenominacion());
            itemDTO.setPrecioCompra(d.getArticuloInsumo().getPrecioCompra());
            itemDTO.setStockActual(d.getArticuloInsumo().getStockActual());

            detalleDTO.setItem(itemDTO);
            return detalleDTO;
        }).collect(Collectors.toList());

        dto.setDetalles(detalles);
        return dto;
    }
}