package br.com.techchallenge4.msPedidos.service;

import br.com.techchallenge4.msPedidos.dto.PedidoRequest;
import br.com.techchallenge4.msPedidos.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidoService {
    Pedido criarPedido(PedidoRequest pedidoRequest);
    Page<Pedido> getPedidos(Pageable pageable);
    Pedido getPedido(Long id);
    Page<Pedido> getPedidosByClienteId(Pageable pageable, Long id);
    Pedido atualizarPedido(Long pedidoId, Pedido pedidoRequest);
    void excluirPedido(Long id);

}
