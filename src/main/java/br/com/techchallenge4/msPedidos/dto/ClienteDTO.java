package br.com.techchallenge4.msPedidos.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ClienteDTO{
    private Long id;
    private String nome;
    private String cpfCnpj;
    private LocalDate dtNascimento;
    private String telefone;
    private String email;
    private List<EnderecoDTO> enderecos;
}
