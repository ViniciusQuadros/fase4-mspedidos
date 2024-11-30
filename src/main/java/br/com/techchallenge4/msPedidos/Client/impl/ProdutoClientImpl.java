package br.com.techchallenge4.msPedidos.Client.impl;

import br.com.techchallenge4.msPedidos.Client.ProdutoClient;
import br.com.techchallenge4.msPedidos.model.Produto;
import br.com.techchallenge4.msPedidos.model.ProdutoEstoque;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ProdutoClientImpl implements ProdutoClient {

//    @Autowired
//    private RestTemplate restTemplate;

    @Override
    public Produto listarUmProduto(Long id) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Produto> response = restTemplate.getForEntity(
                "http://localhost:8082/api/v1/produtos/{id}",
                Produto.class,
                id
        );

        if(response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        } else {
            if(response.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new NoSuchElementException("Produto não encontrado");
            } else {
                throw new NoSuchElementException("Erro ao acessar o serviço Produto");
            }
        }
    }

    @Override
    public void atualizarEstoque(List<ProdutoEstoque> listaProdutoEstoque) {

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.put(
                "http://localhost:8082/api/v1/atualizar/estoque",
                listaProdutoEstoque
        );
    }
}
