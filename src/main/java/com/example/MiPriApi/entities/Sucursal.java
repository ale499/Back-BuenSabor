package com.example.MiPriApi.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sucursales")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sucursal extends Base{

    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private String telefono;
    private String email;

    @OneToOne
    @JoinColumn(name = "domicilioId")
    private Domicilio domicilio;



    @ManyToMany
    @JoinTable(name = "sucursalCategoria",
            joinColumns = @JoinColumn(name = "sucursalId"),
            inverseJoinColumns = @JoinColumn(name = "categoriaId"))
    @Builder.Default
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "sucursalPromocion",
            joinColumns = @JoinColumn(name = "sucursalId"),
            inverseJoinColumns = @JoinColumn(name = "promocionId"))
    @Builder.Default
    private Set<Promocion> promociones = new HashSet<>();


}
