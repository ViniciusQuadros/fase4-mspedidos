package br.com.techchallenge4.msPedidos.Client;

import br.com.techchallenge4.msPedidos.dto.ClienteDTO;

public interface ClienteClient {
    ClienteDTO buscarCliente(Long id);
}
