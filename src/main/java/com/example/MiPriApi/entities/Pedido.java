package com.example.MiPriApi.entities;

import com.example.MiPriApi.entities.enums.Estado;
import com.example.MiPriApi.entities.enums.FormaPago;
import com.example.MiPriApi.entities.enums.TipoEnvio;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pedidos")
public class Pedido extends Base{

    private Integer numeroPedido;
    private LocalTime horaEstimadaFinalizacion;
    private Double total = 0.0;
    private Double totalCosto;
    private LocalDate fechaPedido;

    @ManyToOne
    @JoinColumn(name = "chefId")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "cliente_auth0_id")
    @JsonBackReference
    private ClienteAuth0 clienteAuth0;


    @ManyToOne
    @JoinColumn(name = "sucursalId")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "domicilioId")
    private Domicilio domicilio;

    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    @Enumerated(EnumType.STRING)
    private TipoEnvio tipoEnvio;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Permite la serialización de los detalles del pedido sin causar recursividad infinita
    private List<DetallePedido> detalles; // <-- Agrega esta línea
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return getId() != null && getId().equals(pedido.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}