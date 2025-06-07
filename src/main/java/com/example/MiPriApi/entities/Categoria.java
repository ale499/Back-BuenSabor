package com.example.MiPriApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categorias")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categoria extends Base {

    private String denominacion;
    private Boolean esInsumo;



    @ManyToOne
    @JoinColumn(name = "categoriaPadreId")
    @JsonIgnore
    private Categoria categoriaPadre;

    @OneToMany(mappedBy = "categoriaPadre")
    @Builder.Default
    private Set<Categoria> subcategorias = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "categoriaSucursal",
            joinColumns = @JoinColumn(name = "categoriaId"),
            inverseJoinColumns = @JoinColumn(name = "sucursalId"))
    @Builder.Default
    private Set<Sucursal> sucursales = new HashSet<>();
}