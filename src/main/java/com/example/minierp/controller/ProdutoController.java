package com.example.minierp.controller;

import com.example.minierp.entity.Produto;
import com.example.minierp.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.salvar(produto);
        return new ResponseEntity<>(produtoSalvo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        try {
            Produto produto = produtoService.buscarPorId(id);
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String nome) {
        List<Produto> produtos = produtoService.buscarPorNome(nome);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/estoque-baixo")
    public ResponseEntity<List<Produto>> listarProdutosComEstoqueBaixo() {
        List<Produto> produtos = produtoService.listarProdutosComEstoqueBaixo();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/faixa-preco")
    public ResponseEntity<List<Produto>> buscarPorFaixaPreco(
            @RequestParam BigDecimal precoMin,
            @RequestParam BigDecimal precoMax) {
        List<Produto> produtos = produtoService.buscarPorFaixaPreco(precoMin, precoMax);
        return ResponseEntity.ok(produtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.atualizar(id, produto);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<Void> atualizarEstoque(@PathVariable Long id, @RequestParam Integer quantidade) {
        try {
            produtoService.atualizarEstoque(id, quantidade);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            produtoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
