package com.example.MiPriApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "articuloManufacturadoDetalles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "articuloManufacturado")
@EqualsAndHashCode(exclude = "articuloManufacturado")
@Builder
public class ArticuloManufacturadoDetalle extends Base{

    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "ArticuloManufacturadoId", nullable = false)
    @JsonIgnore
    private ArticuloManufacturado articuloManufacturado;

    @ManyToOne
    @JoinColumn(name = "articuloInsumoId")

    private ArticuloInsumo articuloInsumo;
}
