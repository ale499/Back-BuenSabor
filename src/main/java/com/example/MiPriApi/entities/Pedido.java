package com.example.MiPriApi.entities;

import com.example.MiPriApi.entities.enums.Estado;
import com.example.MiPriApi.entities.enums.FormaPago;
import com.example.MiPriApi.entities.enums.TipoEnvio;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pedido extends Base{

    private Integer numeroPedido;
    private LocalTime horaEstimadaFinalizacion;
    private Double total = 0.0;
    private Double totalCosto;
    private Estado estado;
    private TipoEnvio tipoEnvio;
    private FormaPago formaPago;
    private LocalDate fechaPedido;

    @ManyToOne
    @JoinColumn(name = "chefId")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;


    @ManyToOne
    @JoinColumn(name = "sucursalId")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "domicilioId")
    private Domicilio domicilio;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DetallePedido> detalles; // <-- Agrega esta línea


}