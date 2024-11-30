package br.com.techchallenge4.msPedidos.Client.impl;

import br.com.techchallenge4.msPedidos.Client.ClienteClient;
import br.com.techchallenge4.msPedidos.dto.ClienteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Component
public class ClienteClientImpl implements ClienteClient {

//    @Autowired
//    private RestTemplate restTemplate;

    @Override
    public ClienteDTO buscarCliente(Long id) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ClienteDTO> response = restTemplate.getForEntity(
                "http://localhost:8081/api/v1/clientes/{id}",
                ClienteDTO.class,
                id
        );

        if(response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        } else {
            if(response.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new NoSuchElementException("Cliente não encontrado");
            } else {
                throw new NoSuchElementException("Erro ao acessar o serviço Cliente");
            }
        }

    }
}
