package com.example.MiPriApi.controllers;



import com.example.MiPriApi.entities.DTO.PedidoRequestDTO;
import com.example.MiPriApi.entities.DTO.PedidoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PedidoWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notificarCliente(Long clienteId, PedidoResponseDTO pedido) {
        messagingTemplate.convertAndSend("/topic/pedidos/" + clienteId, pedido);
        messagingTemplate.convertAndSend("/topic/pedidos", pedido);
    }
}