package br.com.techchallenge4.msPedidos.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Produto {

    private Long id;
    private String nome;
    private String descricao;
    private Long qtdEstoque;
    private double preco;
    private boolean ativo;
    private LocalDateTime dtImportacao;

}
