package br.com.techchallenge4.msPedidos.repository;

import br.com.techchallenge4.msPedidos.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Pedido findByClienteId(Long clienteId);

    Page<Pedido> findByClienteId(Long clienteId, Pageable pageable);
}
