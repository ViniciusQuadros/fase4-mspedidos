package br.com.techchallenge4.msPedidos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_items_pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idProduto;
    private Long quantidade;
    private BigDecimal preco;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    @JsonIgnore
    private Pedido pedido;

}
