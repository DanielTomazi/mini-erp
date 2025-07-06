package com.example.minierp.controller;

import com.example.minierp.entity.Produto;
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
@RequestMapping("/produtos")
public class ProdutoWebController {

    @Autowired(required = false)
    private ProdutoService produtoService;

    @GetMapping
    public String listarProdutos(Model model) {
        try {
            List<Produto> produtos = produtoService != null ? produtoService.listarTodos() : new ArrayList<>();
            model.addAttribute("produtos", produtos);
        } catch (Exception e) {
            model.addAttribute("produtos", new ArrayList<>());
            model.addAttribute("erro", "Erro ao carregar produtos: " + e.getMessage());
        }
        return "produtos/lista";
    }

    @GetMapping("/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/form";
    }

    @PostMapping
    public String salvarProduto(@Valid @ModelAttribute Produto produto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "produtos/form";
        }

        try {
            if (produtoService != null) {
                produtoService.salvar(produto);
                redirectAttributes.addFlashAttribute("sucesso", "Produto salvo com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Serviço não disponível");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar produto: " + e.getMessage());
        }

        return "redirect:/produtos";
    }

    @GetMapping("/{id}/editar")
    public String editarProduto(@PathVariable Long id, Model model) {
        try {
            Produto produto = produtoService != null ? produtoService.buscarPorId(id) : new Produto();
            if (produto == null) {
                return "redirect:/produtos";
            }
            model.addAttribute("produto", produto);
        } catch (Exception e) {
            model.addAttribute("produto", new Produto());
            model.addAttribute("erro", "Erro ao carregar produto");
        }
        return "produtos/form";
    }

    @PostMapping("/{id}/deletar")
    public String deletarProduto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (produtoService != null) {
                produtoService.deletar(id);
                redirectAttributes.addFlashAttribute("sucesso", "Produto deletado com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("erro", "Serviço não disponível");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao deletar produto: " + e.getMessage());
        }
        return "redirect:/produtos";
    }
}
