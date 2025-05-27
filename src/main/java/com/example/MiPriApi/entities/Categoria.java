package com.example.MiPriApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @OneToMany(mappedBy = "categoriaPadre")
    @Builder.Default
    private Set<Categoria> subcategorias = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "categoriaPadreId")
    @JsonIgnore
    private Categoria categoriaPadre;

    @ManyToMany
    @JoinTable(name = "categoriaSucursal",
            joinColumns = @JoinColumn(name = "categoriaId"),
            inverseJoinColumns = @JoinColumn(name = "sucursalId"))
    @Builder.Default
    private Set<Sucursal> sucursals = new HashSet<>();
}