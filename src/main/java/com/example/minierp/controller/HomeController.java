package com.example.minierp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return """
            <html>
            <head>
                <title>Mini ERP - Sistema de Gestão</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }
                    .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
                    h1 { color: #2c3e50; border-bottom: 3px solid #3498db; padding-bottom: 10px; }
                    h2 { color: #34495e; margin-top: 30px; }
                    .api-endpoint { background: #ecf0f1; padding: 10px; margin: 5px 0; border-radius: 4px; font-family: monospace; }
                    .method { color: white; padding: 3px 8px; border-radius: 3px; font-size: 12px; font-weight: bold; }
                    .get { background: #27ae60; }
                    .post { background: #e74c3c; }
                    .put { background: #f39c12; }
                    .delete { background: #e67e22; }
                    .credentials { background: #ffffcc; padding: 15px; border-radius: 4px; border-left: 4px solid #f1c40f; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>🚀 Mini ERP - Sistema de Gestão Empresarial</h1>
                    <p>Bem-vindo ao Mini ERP! Este sistema oferece APIs completas para gestão de clientes, produtos e pedidos.</p>
                    
                    <div class="credentials">
                        <strong>🔐 Credenciais de Acesso:</strong><br>
                        Usuário: <code>admin</code><br>
                        Senha: <code>admin123</code>
                    </div>

                    <h2>📋 APIs Disponíveis</h2>

                    <h3>👥 Clientes</h3>
                    <div class="api-endpoint"><span class="method get">GET</span> /api/clientes - Listar todos os clientes</div>
                    <div class="api-endpoint"><span class="method get">GET</span> /api/clientes/{id} - Buscar cliente por ID</div>
                    <div class="api-endpoint"><span class="method get">GET</span> /api/clientes/buscar?nome=... - Buscar por nome</div>
                    <div class="api-endpoint"><span class="method post">POST</span> /api/clientes - Criar novo cliente</div>
                    <div class="api-endpoint"><span class="method put">PUT</span> /api/clientes/{id} - Atualizar cliente</div>
                    <div class="api-endpoint"><span class="method delete">DELETE</span> /api/clientes/{id} - Deletar cliente</div>

                    <h3>📦 Produtos</h3>
                    <div class="api-endpoint"><span class="method get">GET</span> /api/produtos - Listar todos os produtos</div>
                    <div class="api-endpoint"><span class="method get">GET</span> /api/produtos/{id} - Buscar produto por ID</div>
                    <div class="api-endpoint"><span class="method get">GET</span> /api/produtos/estoque-baixo - Produtos com estoque baixo</div>
                    <div class="api-endpoint"><span class="method post">POST</span> /api/produtos - Criar novo produto</div>
                    <div class="api-endpoint"><span class="method put">PUT</span> /api/produtos/{id} - Atualizar produto</div>
                    <div class="api-endpoint"><span class="method delete">DELETE</span> /api/produtos/{id} - Deletar produto</div>

                    <h3>📋 Pedidos</h3>
                    <div class="api-endpoint"><span class="method get">GET</span> /api/pedidos - Listar todos os pedidos</div>
                    <div class="api-endpoint"><span class="method get">GET</span> /api/pedidos/{id} - Buscar pedido por ID</div>
                    <div class="api-endpoint"><span class="method get">GET</span> /api/pedidos/cliente/{clienteId} - Pedidos de um cliente</div>
                    <div class="api-endpoint"><span class="method post">POST</span> /api/pedidos?clienteId=... - Criar novo pedido</div>
                    <div class="api-endpoint"><span class="method post">POST</span> /api/pedidos/{id}/itens - Adicionar item ao pedido</div>

                    <h2>🗄️ Ferramentas</h2>
                    <div class="api-endpoint">
                        <a href="/h2-console" target="_blank">🗄️ Console H2 Database</a> - Visualizar e consultar dados
                        <br><small>JDBC URL: jdbc:h2:mem:minierp | User: sa | Password: (vazio)</small>
                    </div>

                    <h2>📖 Exemplo de Uso</h2>
                    <p>Para testar rapidamente, acesse:</p>
                    <div class="api-endpoint">
                        <a href="/api/clientes" target="_blank">📋 Ver todos os clientes</a>
                    </div>
                    <div class="api-endpoint">
                        <a href="/api/produtos" target="_blank">📦 Ver todos os produtos</a>
                    </div>
                    <div class="api-endpoint">
                        <a href="/api/pedidos" target="_blank">📋 Ver todos os pedidos</a>
                    </div>

                    <footer style="margin-top: 40px; padding-top: 20px; border-top: 1px solid #ddd; color: #7f8c8d; text-align: center;">
                        <p>Mini ERP v1.0.0 - Sistema de Gestão Empresarial</p>
                    </footer>
                </div>
            </body>
            </html>
            """;
    }
}
