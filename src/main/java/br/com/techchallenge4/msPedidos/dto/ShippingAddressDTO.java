package br.com.techchallenge4.msPedidos.dto;

import lombok.Builder;

@Builder
public record ShippingAddressDTO(
        String street,
        Integer number,
        String complement,
        String city,
        String neighborhood,
        String state,
        Integer zipCode,
        String country,
        String phone
) {
}
