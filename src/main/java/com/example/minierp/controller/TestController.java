package com.example.minierp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Teste funcionando! Aplicação está rodando corretamente.";
    }

    @GetMapping("/test-clientes")
    @ResponseBody
    public String testClientes() {
        return "Rota /test-clientes funcionando!";
    }
}
