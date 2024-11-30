package br.com.techchallenge4.msPedidos.dto;

import lombok.Data;

@Data
public class EnderecoDTO{
    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}