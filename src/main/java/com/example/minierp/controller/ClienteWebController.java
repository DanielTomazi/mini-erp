package com.example.minierp.controller;

import com.example.minierp.entity.Cliente;
import com.example.minierp.service.ClienteService;
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
@RequestMapping("/clientes")
public class ClienteWebController {

    @Autowired(required = false)
    private ClienteService clienteService;

    @GetMapping
    public String listarClientes(Model model) {
        try {
            List<Cliente> clientes = clienteService != null ? clienteService.listarTodos() : new ArrayList<>();
            model.addAttribute("clientes", clientes);
        } catch (Exception e) {
            model.addAttribute("clientes", new ArrayList<>());
            model.addAttribute("erro", "Erro ao carregar clientes: " + e.getMessage());
        }
        return "clientes/lista";
    }

    @GetMapping("/novo")
    public String novoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    @PostMapping
    public String salvarCliente(@Valid @ModelAttribute Cliente cliente,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "clientes/form";
        }

        try {
            if (clienteService != null) {
                clienteService.salvar(cliente);
                redirectAttributes.addFlashAttribute("sucesso", "Cliente salvo com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Serviço não disponível");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar cliente: " + e.getMessage());
        }

        return "redirect:/clientes";
    }

    @GetMapping("/{id}/editar")
    public String editarCliente(@PathVariable Long id, Model model) {
        try {
            Cliente cliente = clienteService != null ? clienteService.buscarPorId(id) : new Cliente();
            if (cliente == null) {
                return "redirect:/clientes";
            }
            model.addAttribute("cliente", cliente);
        } catch (Exception e) {
            model.addAttribute("cliente", new Cliente());
            model.addAttribute("erro", "Erro ao carregar cliente");
        }
        return "clientes/form";
    }

    @PostMapping("/{id}/deletar")
    public String deletarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (clienteService != null) {
                clienteService.deletar(id);
                redirectAttributes.addFlashAttribute("sucesso", "Cliente deletado com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Serviço não disponível");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar cliente: " + e.getMessage());
        }
        return "redirect:/clientes";
    }
}
