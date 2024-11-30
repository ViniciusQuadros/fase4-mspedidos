package br.com.techchallenge4.msPedidos.dto;

import lombok.Builder;

@Builder
public record ShippingProductDTO(
        String code,
        String quantity
) {
}
