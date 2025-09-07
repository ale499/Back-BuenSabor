package com.example.MiPriApi.services.Mappers;
import com.example.MiPriApi.entities.DTO.DetallePedidoRequestDTO;
import com.example.MiPriApi.entities.DTO.PedidoResponseDTO;
import com.example.MiPriApi.entities.Pedido;
import com.example.MiPriApi.entities.ArticuloInsumo;
import com.example.MiPriApi.entities.ArticuloManufacturado;
import com.example.MiPriApi.entities.DetallePedido;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoResponseMapper {
    public static PedidoResponseDTO toDTO(Pedido pedido) {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setNumeroPedido(pedido.getNumeroPedido());
        dto.setClienteId(pedido.getClienteAuth0() != null ? pedido.getClienteAuth0().getId() : null);        dto.setEmpleadoId(pedido.getEmpleado() != null ? pedido.getEmpleado().getId() : null);
        dto.setSucursalId(pedido.getSucursal() != null ? pedido.getSucursal().getId() : null);
        dto.setEstado(pedido.getEstado().toString());
        dto.setTotal(pedido.getTotal());
        dto.setTotalCosto(pedido.getTotalCosto());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setHoraEstimadaFinalizacion(pedido.getHoraEstimadaFinalizacion());
        dto.setHoraCreacion(pedido.getHoraCreacion());

        // Calcular minutos estimados
        if (pedido.getHoraEstimadaFinalizacion() != null) {
            int minutos = pedido.getHoraEstimadaFinalizacion().toSecondOfDay() / 60
                    - LocalTime.now().toSecondOfDay() / 60;
            dto.setTiempoEstimadoMinutos(Math.max(minutos, 0));
            dto.setTiempoEstimadoDuracion(LocalTime.of(minutos / 60, minutos % 60, 0));

        }

        List<DetallePedidoRequestDTO> items = new ArrayList<>();
        for (DetallePedido detalle : pedido.getDetalles()) {
            DetallePedidoRequestDTO itemDTO = new DetallePedidoRequestDTO();
            itemDTO.setArticuloId(detalle.getArticulo().getId());
            itemDTO.setCantidad(detalle.getCantidad());
            itemDTO.setSubTotal(detalle.getSubTotal());
            if (detalle.getArticulo() instanceof ArticuloInsumo) {
                itemDTO.setTipoArticulo("INSUMO");
            } else if (detalle.getArticulo() instanceof ArticuloManufacturado) {
                itemDTO.setTipoArticulo("MANUFACTURADO");
            }
            items.add(itemDTO);
        }

        dto.setItems(items);
        return dto;
    }
}
