package com.example.MiPriApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonInclude;
>>>>>>> Dev
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

<<<<<<< HEAD
=======


>>>>>>> Dev
    @ManyToOne
    @JoinColumn(name = "categoriaPadreId")
    @JsonIgnore
    private Categoria categoriaPadre;

<<<<<<< HEAD
=======
    @OneToMany(mappedBy = "categoriaPadre")
    @Builder.Default
    private Set<Categoria> subcategorias = new HashSet<>();

>>>>>>> Dev
    @ManyToMany
    @JoinTable(name = "categoriaSucursal",
            joinColumns = @JoinColumn(name = "categoriaId"),
            inverseJoinColumns = @JoinColumn(name = "sucursalId"))
    @Builder.Default
<<<<<<< HEAD
    private Set<Sucursal> sucursals = new HashSet<>();
=======
    private Set<Sucursal> sucursales = new HashSet<>();
>>>>>>> Dev
}