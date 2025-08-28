package com.example.MiPriApi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cliente_auth0")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteAuth0 extends Base {

    @Column(name = "auth0_id", unique = true, nullable = false)
    private String auth0Id;

    @Column(name = "email")
    private String email; // Para cache/referencia rápida

    @ManyToMany
    @JoinTable(
        name = "cliente_auth0_domicilio",
        joinColumns = @JoinColumn(name = "cliente_auth0_id"),
        inverseJoinColumns = @JoinColumn(name = "domicilio_id")
    )
    @Builder.Default
    private Set<Domicilio> domicilios = new HashSet<>();
}
