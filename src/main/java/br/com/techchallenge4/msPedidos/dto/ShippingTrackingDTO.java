package br.com.techchallenge4.msPedidos.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ShippingTrackingDTO(
        UUID trackingId,
        Double latitude,
        Double longitude,
        ShippingCarrierDTO carrier
) {
}
