package br.com.techchallenge4.msPedidos.dto;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequest {

    private Long clienteId;
    private List<ItemPedidoRequest> itensPedido;
}