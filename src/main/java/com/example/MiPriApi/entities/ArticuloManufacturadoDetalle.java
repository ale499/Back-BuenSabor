package com.example.MiPriApi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "articuloManufacturadoDetalles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "detalles")
@EqualsAndHashCode(exclude = "detalles")
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
