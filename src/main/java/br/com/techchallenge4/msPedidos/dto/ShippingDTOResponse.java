package br.com.techchallenge4.msPedidos.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record ShippingDTOResponse(
        UUID id,
        String orderCode,
        String recipient,
        String status,
        LocalDate shippingDate,
        LocalDate deliveryDate,
        ShippingTrackingDTO tracking,
        ShippingAddressDTO address,
        List<ShippingProductDTO> products
){}
