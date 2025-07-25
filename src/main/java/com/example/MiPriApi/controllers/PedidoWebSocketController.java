package com.example.MiPriApi.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.MiPriApi.entities.DTO.PedidoRequestDTO;

@Controller
public class PedidoWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notificarCliente(Long clienteId, PedidoRequestDTO pedido) {
        messagingTemplate.convertAndSend("/topic/pedidos/" + clienteId, pedido);
    }
}