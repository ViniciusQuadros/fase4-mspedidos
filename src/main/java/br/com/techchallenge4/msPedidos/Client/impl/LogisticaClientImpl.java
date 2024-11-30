package br.com.techchallenge4.msPedidos.Client.impl;

import br.com.techchallenge4.msPedidos.Client.LogisticaClient;
import br.com.techchallenge4.msPedidos.dto.ClienteDTO;
import br.com.techchallenge4.msPedidos.dto.ShippingDTORequest;
import br.com.techchallenge4.msPedidos.dto.ShippingDTOResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Component
public class LogisticaClientImpl implements LogisticaClient {

//    @Autowired
//    private RestTemplate restTemplate;

    @Override
    public ShippingDTOResponse criarLogistica(ShippingDTORequest shippingDTO) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ShippingDTOResponse> response = restTemplate.postForEntity("http://localhost:8084/api/v1/shippings",
                shippingDTO, ShippingDTOResponse.class);

        if(response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        } else {
                throw new NoSuchElementException("Erro ao acessar o serviço Logistica");
        }
    }
}
