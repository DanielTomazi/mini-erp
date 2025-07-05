package com.example.minierp.service;

import com.example.minierp.entity.*;
import com.example.minierp.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    public Pedido criarPedido(Long clienteId) {
        Cliente cliente = clienteService.buscarPorId(clienteId);
        return new Pedido(cliente);
    }

    public Pedido adicionarItem(Long pedidoId, Long produtoId, Integer quantidade) {
        Pedido pedido = buscarPorId(pedidoId);
        Produto produto = produtoService.buscarPorId(produtoId);

        // Verificar disponibilidade em estoque
        if (!produtoService.verificarDisponibilidadeEstoque(produtoId, quantidade)) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
        }

        ItemPedido item = new ItemPedido(produto, quantidade, produto.getPreco());
        pedido.adicionarItem(item);

        return pedidoRepository.save(pedido);
    }

    public Pedido confirmarPedido(Long pedidoId) {
        Pedido pedido = buscarPorId(pedidoId);

        if (pedido.getItens().isEmpty()) {
            throw new IllegalArgumentException("Pedido deve ter pelo menos um item");
        }

        // Atualizar estoque dos produtos
        for (ItemPedido item : pedido.getItens()) {
            produtoService.atualizarEstoque(item.getProduto().getId(), -item.getQuantidade());
        }

        pedido.setStatus(StatusPedido.CONFIRMADO);
        return pedidoRepository.save(pedido);
    }

    public Pedido atualizarStatus(Long pedidoId, StatusPedido novoStatus) {
        Pedido pedido = buscarPorId(pedidoId);
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }

    public Pedido cancelarPedido(Long pedidoId) {
        Pedido pedido = buscarPorId(pedidoId);

        if (pedido.getStatus() == StatusPedido.CONFIRMADO ||
            pedido.getStatus() == StatusPedido.PROCESSANDO) {
            // Reverter estoque
            for (ItemPedido item : pedido.getItens()) {
                produtoService.atualizarEstoque(item.getProduto().getId(), item.getQuantidade());
            }
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Pedido> buscarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Transactional(readOnly = true)
    public List<Pedido> buscarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Pedido> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return pedidoRepository.findByDataPedidoBetween(dataInicio, dataFim);
    }
}
