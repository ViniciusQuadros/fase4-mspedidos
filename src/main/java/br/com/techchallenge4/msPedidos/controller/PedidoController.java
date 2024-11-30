package br.com.techchallenge4.msPedidos.controller;

import br.com.techchallenge4.msPedidos.dto.PedidoRequest;
import br.com.techchallenge4.msPedidos.model.Pedido;
import br.com.techchallenge4.msPedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<Page<Pedido>> findAll(@PageableDefault(page = 0, size = 10, sort = "nome") Pageable pageable) {
        Page<Pedido> pedidos = pedidoService.getPedidos(pageable);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Long id) {
        Pedido pedido = pedidoService.getPedido(id);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoRequest pedido){
        Pedido pedidoResponse = pedidoService.criarPedido(pedido);
        return new ResponseEntity<>(pedidoResponse, HttpStatus.CREATED);
    }

}