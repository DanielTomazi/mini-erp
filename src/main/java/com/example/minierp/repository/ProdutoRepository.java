package com.example.minierp.repository;

import com.example.minierp.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque <= p.estoqueMinimo")
    List<Produto> findProdutosComEstoqueBaixo();

    List<Produto> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);

    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque > 0")
    List<Produto> findProdutosEmEstoque();
}
