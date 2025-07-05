package com.example.minierp.service;

import com.example.minierp.entity.Produto;
import com.example.minierp.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto("Notebook", "Notebook Dell", new BigDecimal("2500.00"), 10);
        produto.setId(1L);
        produto.setEstoqueMinimo(5);
    }

    @Test
    void deveSalvarProdutoComSucesso() {
        // Given
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        // When
        Produto produtoSalvo = produtoService.salvar(produto);

        // Then
        assertNotNull(produtoSalvo);
        assertEquals("Notebook", produtoSalvo.getNome());
        assertEquals(new BigDecimal("2500.00"), produtoSalvo.getPreco());
        verify(produtoRepository).save(produto);
    }

    @Test
    void deveBuscarProdutoPorId() {
        // Given
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        // When
        Produto produtoEncontrado = produtoService.buscarPorId(1L);

        // Then
        assertNotNull(produtoEncontrado);
        assertEquals(1L, produtoEncontrado.getId());
        assertEquals("Notebook", produtoEncontrado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        // Given
        when(produtoRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> produtoService.buscarPorId(999L)
        );
        assertEquals("Produto não encontrado", exception.getMessage());
    }

    @Test
    void deveAtualizarEstoqueComSucesso() {
        // Given
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        // When
        produtoService.atualizarEstoque(1L, 5);

        // Then
        assertEquals(15, produto.getQuantidadeEstoque());
        verify(produtoRepository).save(produto);
    }

    @Test
    void deveVerificarDisponibilidadeEstoque() {
        // Given
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        // When
        boolean disponivel = produtoService.verificarDisponibilidadeEstoque(1L, 5);
        boolean indisponivel = produtoService.verificarDisponibilidadeEstoque(1L, 15);

        // Then
        assertTrue(disponivel);
        assertFalse(indisponivel);
    }

    @Test
    void deveListarProdutosComEstoqueBaixo() {
        // Given
        produto.setQuantidadeEstoque(3); // Abaixo do mínimo
        List<Produto> produtos = Arrays.asList(produto);
        when(produtoRepository.findProdutosComEstoqueBaixo()).thenReturn(produtos);

        // When
        List<Produto> produtosEstoqueBaixo = produtoService.listarProdutosComEstoqueBaixo();

        // Then
        assertEquals(1, produtosEstoqueBaixo.size());
        assertTrue(produtosEstoqueBaixo.get(0).isEstoqueBaixo());
    }

    @Test
    void deveBuscarProdutosPorFaixaPreco() {
        // Given
        BigDecimal precoMin = new BigDecimal("2000.00");
        BigDecimal precoMax = new BigDecimal("3000.00");
        List<Produto> produtos = Arrays.asList(produto);
        when(produtoRepository.findByPrecoBetween(precoMin, precoMax)).thenReturn(produtos);

        // When
        List<Produto> produtosEncontrados = produtoService.buscarPorFaixaPreco(precoMin, precoMax);

        // Then
        assertEquals(1, produtosEncontrados.size());
        assertEquals("Notebook", produtosEncontrados.get(0).getNome());
    }
}
