package com.example.MiPriApi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Articulo extends Base{

    protected String denominacion;

    @ManyToOne
    @JoinColumn(name = "categoriaId")
    protected Categoria categoria;

    @OneToMany
    @Builder.Default
    protected Set<Image> imagenesArticulos = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "unidadMedidaId")
    protected UnidadMedida unidadMedida;

    protected Double precioVenta;
    private int tiempoPreparacion;
}
