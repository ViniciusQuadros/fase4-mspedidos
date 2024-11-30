package br.com.techchallenge4.msPedidos.dto;

import lombok.Data;

@Data
public class ItemPedidoRequest {
    private Long idProduto;
    private int quantidade;
}
