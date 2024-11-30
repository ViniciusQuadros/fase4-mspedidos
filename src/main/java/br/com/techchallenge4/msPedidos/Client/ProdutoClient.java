package br.com.techchallenge4.msPedidos.Client;

import br.com.techchallenge4.msPedidos.model.Produto;
import br.com.techchallenge4.msPedidos.model.ProdutoEstoque;

import java.util.List;

public interface ProdutoClient {
    Produto listarUmProduto(Long id);
    void atualizarEstoque(List<ProdutoEstoque> listaProdutoEstoque);
}
