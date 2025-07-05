package com.example.minierp.controller;

import com.example.minierp.entity.Pedido;
import com.example.minierp.entity.StatusPedido;
import com.example.minierp.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestParam Long clienteId) {
        try {
            Pedido pedido = pedidoService.criarPedido(clienteId);
            return new ResponseEntity<>(pedido, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{pedidoId}/itens")
    public ResponseEntity<Pedido> adicionarItem(
            @PathVariable Long pedidoId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade) {
        try {
            Pedido pedido = pedidoService.adicionarItem(pedidoId, produtoId, quantidade);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{pedidoId}/confirmar")
    public ResponseEntity<Pedido> confirmarPedido(@PathVariable Long pedidoId) {
        try {
            Pedido pedido = pedidoService.confirmarPedido(pedidoId);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{pedidoId}/status")
    public ResponseEntity<Pedido> atualizarStatus(
            @PathVariable Long pedidoId,
            @RequestParam StatusPedido status) {
        try {
            Pedido pedido = pedidoService.atualizarStatus(pedidoId, status);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{pedidoId}/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable Long pedidoId) {
        try {
            Pedido pedido = pedidoService.cancelarPedido(pedidoId);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.buscarPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable Long clienteId) {
        List<Pedido> pedidos = pedidoService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pedido>> buscarPorStatus(@PathVariable StatusPedido status) {
        List<Pedido> pedidos = pedidoService.buscarPorStatus(status);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Pedido>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        List<Pedido> pedidos = pedidoService.buscarPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(pedidos);
    }
}
