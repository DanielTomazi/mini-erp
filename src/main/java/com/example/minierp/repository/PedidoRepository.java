package com.example.minierp.repository;

import com.example.minierp.entity.Pedido;
import com.example.minierp.entity.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByStatus(StatusPedido status);

    @Query("SELECT p FROM Pedido p WHERE p.dataPedido BETWEEN :dataInicio AND :dataFim")
    List<Pedido> findByDataPedidoBetween(@Param("dataInicio") LocalDateTime dataInicio,
                                        @Param("dataFim") LocalDateTime dataFim);

    @Query("SELECT p FROM Pedido p JOIN FETCH p.itens WHERE p.id = :id")
    Pedido findByIdWithItens(@Param("id") Long id);
}
