package com.example.MiPriApi.entities;

import com.example.MiPriApi.entities.enums.TipoPromocion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "promociones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promocion extends Base{

    private String denominacion;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private String descripcionDescuento;
    private Double precioPromocional;
    private TipoPromocion tipoPromocion;

    @OneToMany
    @Builder.Default
    private Set<Imagen> imagenesPromocion = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "promocionSucursal",
            joinColumns = @JoinColumn(name = "promocionId"),
            inverseJoinColumns = @JoinColumn(name = "sucursalId"))
    @Builder.Default
    private Set<Sucursal> sucursales = new HashSet<>();
}