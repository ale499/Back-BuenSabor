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
@Table(name = "detallePromociones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetallePromocion extends Base{

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "detallePromocionId", nullable = false)
    private Promocion promocion;

    @ManyToOne
    @JoinColumn(name = "articuloId")
    private Articulo articulo;
}
