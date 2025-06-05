package com.example.MiPriApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Cliente extends Persona{



    @ManyToMany
    @JoinTable(name = "clienteDomicilio",
            joinColumns = @JoinColumn(name = "clienteId"),
            inverseJoinColumns = @JoinColumn(name = "domicilioId"))
    @Builder.Default
    private Set<Domicilio> domicilios = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "usuarioCliente")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "imagenCliente")
    private Imagen imagen;


}
