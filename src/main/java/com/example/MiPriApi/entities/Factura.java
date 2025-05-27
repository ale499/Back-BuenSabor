package com.example.MiPriApi.entities;

import com.example.MiPriApi.entities.enums.FormaPago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "facturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Factura extends Base{

    private LocalDate fechaFacturacion;

    private Integer mpPaymentId;

    private Integer mpMerchantOrderId;

    private String mpPreferenceId;

    private String mpPaymentType;
    private FormaPago formaPago;
    private Double totalVenta;

    @OneToOne
    @JoinColumn(name = "pedidoId")
    private Pedido pedido;

}
