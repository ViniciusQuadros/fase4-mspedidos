package br.com.techchallenge4.msPedidos.Client;

import br.com.techchallenge4.msPedidos.dto.ShippingDTORequest;
import br.com.techchallenge4.msPedidos.dto.ShippingDTOResponse;

public interface LogisticaClient {
    ShippingDTOResponse criarLogistica(ShippingDTORequest shippingDTO);
}
