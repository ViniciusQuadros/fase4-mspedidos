package br.com.techchallenge4.msPedidos.dto;

import lombok.Builder;

@Builder
public record ShippingCarrierDTO(
        Integer code,
        String name,
        Integer zipCodeStart,
        Integer zipCodeEnd
) {
}
