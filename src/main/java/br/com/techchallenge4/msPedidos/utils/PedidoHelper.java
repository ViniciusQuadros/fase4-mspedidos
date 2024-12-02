package br.com.techchallenge4.msPedidos.utils;

import br.com.techchallenge4.msPedidos.dto.ItemPedidoRequest;
import br.com.techchallenge4.msPedidos.dto.PedidoRequest;
import br.com.techchallenge4.msPedidos.model.ItemPedido;
import br.com.techchallenge4.msPedidos.model.Pedido;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public abstract class PedidoHelper {

    public static PedidoRequest criarPedidoRequest(){

        ItemPedidoRequest itemPedidoRequest = new ItemPedidoRequest();
        itemPedidoRequest.setIdProduto(1L);
        itemPedidoRequest.setQuantidade(1L);

        List<ItemPedidoRequest> itemspedidoRequest = List.of(itemPedidoRequest);

        PedidoRequest pedidoRequest = new PedidoRequest();
        pedidoRequest.setClienteId(1L);
        pedidoRequest.setItensPedido(itemspedidoRequest);

        return pedidoRequest;
    }

    public static Pedido criarPedido(){

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(1L);
        itemPedido.setIdProduto(1L);
        itemPedido.setQuantidade(1L);
        itemPedido.setPreco(new BigDecimal("10"));

        List<ItemPedido> itemsPedido = List.of(itemPedido);

        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setNomeCliente("nomeCliente");
        pedido.setClienteId(1L);
        pedido.setItensPedido(itemsPedido);
        pedido.setValorTotal(new BigDecimal("10"));
        pedido.setIdLogistica(UUID.fromString("3d6f0eb8-d1b1-4b7c-9cf8-8e4d6d690b49"));

        return pedido;
    }

}
