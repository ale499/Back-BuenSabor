package com.example.MiPriApi.services;

import com.example.MiPriApi.entities.DTO.ItemDTO;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    @Value("${mercado-pago.access-token}")
    private String accessToken;

    public String crearPreferencia(List<PreferenceItemRequest> items, Long idPedido) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(accessToken);

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("https://www.mendoza.gov.ar/prensa/noticia-destacada/&id=" + idPedido)
                .failure("http://localhost:5173/payment-return?status=failure&id=" + idPedido)
                .pending("http://localhost:5173/payment-return?status=pending&id=" + idPedido)
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved")
                .build();

        PreferenceClient client = new PreferenceClient();

        try {
            Preference preference = client.create(preferenceRequest);
            return preference.getInitPoint();
        } catch (MPApiException e) {
            System.err.println("Error al procesar la preferencia: " + e.getApiResponse().getContent());
            throw e;
        }
    }

    public PreferenceItemRequest crearItem(String nombre, int cantidad, BigDecimal precioUnitario) {
        return PreferenceItemRequest.builder()
                .title(nombre)
                .quantity(cantidad)
                .unitPrice(precioUnitario)
                .currencyId("ARS")
                .build();
    }

    public String procesarPago(List<ItemDTO> itemsDTO, Long idPedido) throws MPException, MPApiException {
        List<PreferenceItemRequest> itemsMP = new ArrayList<>();

        for (ItemDTO item : itemsDTO) {
            itemsMP.add(crearItem(
                    item.getNombre(),
                    item.getCantidad(),
                    item.getPrecio()
            ));
        }

        return crearPreferencia(itemsMP, idPedido);
    }
}