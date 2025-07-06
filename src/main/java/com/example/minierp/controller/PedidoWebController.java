package com.example.minierp.controller;

import com.example.minierp.entity.Pedido;
import com.example.minierp.service.ClienteService;
import com.example.minierp.service.PedidoService;
import com.example.minierp.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoWebController {

    @Autowired(required = false)
    private PedidoService pedidoService;

    @Autowired(required = false)
    private ClienteService clienteService;

    @Autowired(required = false)
    private ProdutoService produtoService;

    @GetMapping
    public String listarPedidos(Model model) {
        try {
            List<Pedido> pedidos = pedidoService != null ? pedidoService.listarTodos() : new ArrayList<>();
            model.addAttribute("pedidos", pedidos);
        } catch (Exception e) {
            model.addAttribute("pedidos", new ArrayList<>());
            model.addAttribute("erro", "Erro ao carregar pedidos: " + e.getMessage());
        }
        return "pedidos/lista";
    }

    @GetMapping("/novo")
    public String novoPedido(Model model) {
        model.addAttribute("pedido", new Pedido());
        try {
            model.addAttribute("clientes", clienteService != null ? clienteService.listarTodos() : new ArrayList<>());
            model.addAttribute("produtos", produtoService != null ? produtoService.listarTodos() : new ArrayList<>());
        } catch (Exception e) {
            model.addAttribute("clientes", new ArrayList<>());
            model.addAttribute("produtos", new ArrayList<>());
            model.addAttribute("erro", "Erro ao carregar dados: " + e.getMessage());
        }
        return "pedidos/form";
    }

    @PostMapping
    public String salvarPedido(@Valid @ModelAttribute Pedido pedido,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            try {
                model.addAttribute("clientes", clienteService != null ? clienteService.listarTodos() : new ArrayList<>());
                model.addAttribute("produtos", produtoService != null ? produtoService.listarTodos() : new ArrayList<>());
            } catch (Exception e) {
                model.addAttribute("clientes", new ArrayList<>());
                model.addAttribute("produtos", new ArrayList<>());
            }
            return "pedidos/form";
        }

        try {
            if (pedidoService != null) {
                pedidoService.salvar(pedido);
                redirectAttributes.addFlashAttribute("sucesso", "Pedido salvo com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Serviço não disponível");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar pedido: " + e.getMessage());
        }

        return "redirect:/pedidos";
    }

    @GetMapping("/{id}")
    public String verPedido(@PathVariable Long id, Model model) {
        try {
            Pedido pedido = pedidoService != null ? pedidoService.buscarPorId(id) : null;
            if (pedido == null) {
                return "redirect:/pedidos";
            }
            model.addAttribute("pedido", pedido);
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar pedido: " + e.getMessage());
            return "redirect:/pedidos";
        }
        return "pedidos/detalhes";
    }

    @PostMapping("/{id}/deletar")
    public String deletarPedido(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (pedidoService != null) {
                pedidoService.deletar(id);
                redirectAttributes.addFlashAttribute("sucesso", "Pedido deletado com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Serviço não disponível");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar pedido: " + e.getMessage());
        }
        return "redirect:/pedidos";
    }
}
