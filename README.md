# Mini ERP - Sistema de Gestão Empresarial

Um sistema Mini ERP desenvolvido com Java, Spring Boot, PostgreSQL e Docker para gestão de clientes, produtos e pedidos.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL**
- **Docker & Docker Compose**
- **JUnit 5 & Mockito** (para testes)
- **Maven** (gerenciamento de dependências)

## 📋 Funcionalidades

### Gestão de Clientes
- Cadastro, consulta, atualização e exclusão de clientes
- Busca por nome e email
- Validação de email único

### Gestão de Produtos
- Cadastro, consulta, atualização e exclusão de produtos
- Controle de estoque
- Alertas de estoque baixo
- Busca por nome e faixa de preço

### Gestão de Pedidos
- Criação de pedidos
- Adição de itens aos pedidos
- Controle de status do pedido
- Cálculo automático de valores
- Atualização automática de estoque

## 🛠️ Como Executar

### Pré-requisitos
- Docker e Docker Compose instalados
- Java 17+ (para desenvolvimento)
- Maven 3.6+ (para desenvolvimento)

### Executando com Docker

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd MiniERP
```

2. Execute o build da aplicação:
```bash
mvn clean package -DskipTests
```

3. Inicie os serviços:
```bash
docker-compose up -d
```

A aplicação estará disponível em: `http://localhost:8080/mini-erp`

### Executando Localmente

1. Certifique-se de que o PostgreSQL está rodando na porta 5432
2. Configure as variáveis de ambiente ou ajuste o `application.yml`
3. Execute:
```bash
mvn spring-boot:run
```

## 📊 API Endpoints

### Autenticação
- **Usuário:** admin
- **Senha:** admin123

### Clientes
- `GET /api/clientes` - Listar todos os clientes
- `GET /api/clientes/{id}` - Buscar cliente por ID
- `GET /api/clientes/buscar?nome=` - Buscar por nome
- `POST /api/clientes` - Criar novo cliente
- `PUT /api/clientes/{id}` - Atualizar cliente
- `DELETE /api/clientes/{id}` - Deletar cliente

### Produtos
- `GET /api/produtos` - Listar todos os produtos
- `GET /api/produtos/{id}` - Buscar produto por ID
- `GET /api/produtos/buscar?nome=` - Buscar por nome
- `GET /api/produtos/estoque-baixo` - Produtos com estoque baixo
- `POST /api/produtos` - Criar novo produto
- `PUT /api/produtos/{id}` - Atualizar produto
- `PATCH /api/produtos/{id}/estoque?quantidade=` - Atualizar estoque
- `DELETE /api/produtos/{id}` - Deletar produto

### Pedidos
- `GET /api/pedidos` - Listar todos os pedidos
- `GET /api/pedidos/{id}` - Buscar pedido por ID
- `GET /api/pedidos/cliente/{clienteId}` - Pedidos de um cliente
- `GET /api/pedidos/status/{status}` - Pedidos por status
- `POST /api/pedidos?clienteId=` - Criar novo pedido
- `POST /api/pedidos/{id}/itens?produtoId=&quantidade=` - Adicionar item
- `PUT /api/pedidos/{id}/confirmar` - Confirmar pedido
- `PUT /api/pedidos/{id}/cancelar` - Cancelar pedido

## 🧪 Executando Testes

```bash
mvn test
```

## 📄 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/example/minierp/
│   │   ├── config/          # Configurações
│   │   ├── controller/      # Controllers REST
│   │   ├── entity/          # Entidades JPA
│   │   ├── repository/      # Repositórios
│   │   └── service/         # Serviços de negócio
│   └── resources/
│       └── application.yml  # Configurações da aplicação
└── test/
    └── java/                # Testes unitários
```

## 🔒 Segurança

A aplicação utiliza Spring Security com autenticação básica HTTP. Para acessar as APIs, use:
- **Username:** admin
- **Password:** admin123

## 📝 Exemplos de Uso

### Criar um Cliente
```bash
curl -X POST http://localhost:8080/mini-erp/api/clientes \
  -H "Content-Type: application/json" \
  -u admin:admin123 \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "telefone": "11999999999"
  }'
```

### Criar um Produto
```bash
curl -X POST http://localhost:8080/mini-erp/api/produtos \
  -H "Content-Type: application/json" \
  -u admin:admin123 \
  -d '{
    "nome": "Notebook Dell",
    "descricao": "Notebook Dell Inspiron 15",
    "preco": 2500.00,
    "quantidadeEstoque": 10,
    "estoqueMinimo": 5
  }'
```

## 🐳 Docker

O projeto inclui:
- `Dockerfile` para a aplicação Spring Boot
- `docker-compose.yml` para orquestrar aplicação e PostgreSQL
- Volume persistente para dados do PostgreSQL
