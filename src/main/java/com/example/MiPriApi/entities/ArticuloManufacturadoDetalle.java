package com.example.MiPriApi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articuloManufacturadoDetalles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticuloManufacturadoDetalle extends Base{

    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "ArticuloManufacturadoId", nullable = false)
    private ArticuloManufacturado articuloManufacturado;

    @ManyToOne
    @JoinColumn(name = "articuloInsumoId")
    private ArticuloInsumo articuloInsumo;
}
