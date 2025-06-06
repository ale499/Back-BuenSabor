package com.example.MiPriApi.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "domicilios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Domicilio extends Base{

    private String calle;

    private Integer numero;
    private Integer piso;
    private Integer nroDpto;
    @Column(name = "codigoPostal")
    private Integer cp;

    @ManyToOne
    @JoinColumn(name = "localidadId")
    private Localidad localidad;

    @ManyToMany
    @JoinTable(name = "domicilioCliente",
            joinColumns = @JoinColumn(name = "domicilioId"),
            inverseJoinColumns = @JoinColumn(name = "clienteId"))
    @Builder.Default
    private Set<Cliente> clientes = new HashSet<>();
}
