package br.com.techchallenge4.msPedidos.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ShippingDTORequest(
        String orderCode,
        String recipient,
        ShippingAddressDTO address,
        List<ShippingProductDTO> products
){}
