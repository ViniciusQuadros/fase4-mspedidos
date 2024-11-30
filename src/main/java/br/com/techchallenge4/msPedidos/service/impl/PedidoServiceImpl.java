package br.com.techchallenge4.msPedidos.service.impl;

import br.com.techchallenge4.msPedidos.Client.impl.ClienteClientImpl;
import br.com.techchallenge4.msPedidos.Client.impl.LogisticaClientImpl;
import br.com.techchallenge4.msPedidos.Client.impl.ProdutoClientImpl;
import br.com.techchallenge4.msPedidos.dto.*;
import br.com.techchallenge4.msPedidos.model.ItemPedido;
import br.com.techchallenge4.msPedidos.model.Pedido;
import br.com.techchallenge4.msPedidos.model.Produto;
import br.com.techchallenge4.msPedidos.model.ProdutoEstoque;
import br.com.techchallenge4.msPedidos.repository.PedidoRepository;
import br.com.techchallenge4.msPedidos.service.PedidoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    ClienteClientImpl clienteClient;

    @Autowired
    ProdutoClientImpl produtoClient;

    @Autowired
    LogisticaClientImpl logisticaClient;

    @Override
    public Pedido criarPedido(PedidoRequest pedidoRequest) {

        Pedido pedido = toPedido(pedidoRequest);

        ClienteDTO cliente = clienteClient.buscarCliente(pedido.getClienteId());
        pedido.setNomeCliente(cliente.getNome());

        BigDecimal valorTotal = BigDecimal.ZERO;
        for(ItemPedido itemPedido : pedido.getItensPedido()) {
            Produto produto = produtoClient.listarUmProduto(itemPedido.getIdProduto());
            if(produto.getQtdEstoque()<itemPedido.getQuantidade()) {
                throw new NoSuchElementException("Produto" + produto.getId() + "não disponível em estoque na quantidade solicitada. Existem apenas: " + produto.getQtdEstoque());
            }
            if(!produto.isAtivo()){
                throw new NoSuchElementException("Produto não está ativo para compra");
            }
            itemPedido.setPreco(BigDecimal.valueOf(produto.getPreco()));
            valorTotal = valorTotal.add(itemPedido.getPreco().multiply(BigDecimal.valueOf(itemPedido.getQuantidade())));
        }
        pedido.setValorTotal(valorTotal);

        List<ProdutoEstoque> listaProdutoEstoque = new ArrayList<>();
        for(ItemPedido itemPedido : pedido.getItensPedido()) {
            listaProdutoEstoque.add(new ProdutoEstoque(itemPedido.getIdProduto(),itemPedido.getQuantidade()));
        }
        produtoClient.atualizarEstoque(listaProdutoEstoque);

        ShippingDTOResponse shippingDTOResponse = enviarPedidoLogistica(cliente, pedido);

        pedido.setIdLogistica(shippingDTOResponse.id());

        return pedidoRepository.save(pedido);
    }

    private ShippingDTOResponse enviarPedidoLogistica(ClienteDTO cliente, Pedido pedido) {
        ShippingAddressDTO shippingAddressDTO = ShippingAddressDTO.builder()
                .street(cliente.getEnderecos().get(0).getLogradouro())
                .number(Integer.valueOf(cliente.getEnderecos().get(0).getNumero()))
                .complement(cliente.getEnderecos().get(0).getComplemento())
                .city(cliente.getEnderecos().get(0).getCidade())
                .neighborhood(cliente.getEnderecos().get(0).getBairro())
                .state(cliente.getEnderecos().get(0).getEstado())
                .zipCode(Integer.valueOf(cliente.getEnderecos().get(0).getCep()))
                .country("Brazil")
                .phone(null)
                .build();

        List<ShippingProductDTO> shippingProductDTOList = new ArrayList<>();
        for(ItemPedido itemPedido : pedido.getItensPedido()) {
            shippingProductDTOList.add(ShippingProductDTO.builder()
                    .code(String.valueOf(itemPedido.getIdProduto()))
                    .quantity(String.valueOf(itemPedido.getQuantidade()))
                    .build());
        }

        ShippingDTORequest shippingDTORequest = ShippingDTORequest.builder()
                .orderCode(pedido.getId().toString())
                .recipient(pedido.getNomeCliente())
                .address(shippingAddressDTO)
                .products(shippingProductDTOList)
                .build();

        ShippingDTOResponse shippingDTOResponse = logisticaClient.criarLogistica(shippingDTORequest);
        return shippingDTOResponse;
    }

    private static Pedido toPedido(PedidoRequest pedidoRequest) {
        Pedido pedido = new Pedido();
        pedido.setClienteId(pedidoRequest.getClienteId());
        List<ItemPedido> itensPedido = new ArrayList<>();
        for(ItemPedidoRequest itemPedidoRequest : pedidoRequest.getItensPedido()){
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setIdProduto(itemPedidoRequest.getIdProduto());
            itemPedido.setQuantidade(itemPedidoRequest.getQuantidade());
            itensPedido.add(itemPedido);
        }
        pedido.setItensPedido(itensPedido);
        return pedido;
    }

    @Override
    public Page<Pedido> getPedidos(Pageable pageable) {

        return pedidoRepository.findAll(pageable);
    }

    @Override
    public Pedido getPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado."));
    }

    @Override
    public Page<Pedido> getPedidosByClienteId(Pageable pageable, Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId, pageable);
    }

    @Override
    public Pedido atualizarPedido(Long pedidoId, Pedido pedidoRequest) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId);
        if(pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            BeanUtils.copyProperties(pedidoRequest, pedido);
            return pedidoRepository.save(pedido);
        } else {
            throw new NoSuchElementException("Pedido não encontrado.");
        }
    }

    @Override
    public void excluirPedido(Long id) {

    }
}