# CWCDEV API

## Descrição
A **CWCDEV API** é uma API RESTful desenvolvida para gerenciar códigos de programação. Ela permite criar, listar, atualizar e excluir códigos, bem como obter informações do usuário autenticado.

## Tecnologias Utilizadas
- **Java Spring Boot**
- **PostgreSQL**
- **Swagger OpenAPI**
- **Autenticação via Bearer Token**

## Endpoints

### Code Controller

#### Obter código por ID
- **GET** `/codigos/{id}`
- **Descrição:** Retorna um código específico pelo ID.
- **Parâmetros:**
  - `id` (integer, required) - ID do código a ser buscado.
- **Respostas:**
  - `200 OK` - Retorna o objeto `CodigoDTO`
  - `404 Not Found` - Código não encontrado

#### Atualizar código
- **PUT** `/codigos/{id}`
- **Descrição:** Atualiza um código existente.
- **Parâmetros:**
  - `id` (integer, required) - ID do código a ser atualizado.
- **Body:** `CodigoDTO`
- **Respostas:**
  - `200 OK` - Código atualizado com sucesso.
  - `400 Bad Request` - Requisição inválida.
  - `422 Unprocessable Entity` - Dados inválidos.

#### Excluir código
- **DELETE** `/codigos/{id}`
- **Descrição:** Remove um código pelo ID.
- **Parâmetros:**
  - `id` (integer, required) - ID do código a ser deletado.
- **Respostas:**
  - `200 OK` - Código deletado com sucesso.

#### Listar todos os códigos
- **GET** `/codigos`
- **Descrição:** Retorna todos os códigos cadastrados.
- **Respostas:**
  - `200 OK` - Retorna uma lista de `CodigoDTO`

#### Criar novo código
- **POST** `/codigos`
- **Descrição:** Cria um novo código.
- **Body:** `CodigoDTO`
- **Respostas:**
  - `201 Created` - Código criado com sucesso.
  - `400 Bad Request` - Requisição inválida.
  - `422 Unprocessable Entity` - Dados inválidos.

### User Controller

#### Obter informações do usuário autenticado
- **GET** `/users/me`
- **Descrição:** Retorna os dados do usuário autenticado.
- **Respostas:**
  - `200 OK` - Retorna o objeto `UserDTO`.

## Modelos de Dados

### CodigoDTO
```json
{
  "id": 1,
  "linguagem": "Java",
  "descricao": "Exemplo de código Java",
  "codigo": "public class HelloWorld { ... }",
  "imgUrl": "https://example.com/image.png"
}
```

### UserDTO
```json
{
  "id": 1,
  "name": "Calebe Werneck Couto",
  "email": "calebewerneck@hotmail.com",
  "phone": "31 98796-7617",
  "birthDate": "1990-01-01",
  "roles": ["USER"]
}
```

## Autenticação
A API utiliza autenticação via Bearer Token. Para acessar endpoints protegidos, inclua o token no cabeçalho `Authorization`:
```bash
Authorization: Bearer <seu_token_aqui>
```

## Como Rodar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/calebewerneckcouto/CWCDEV.git
   ```
2. Acesse a pasta do projeto:
   ```bash
   cd CWCDEV
   ```
3. Configure o banco de dados PostgreSQL no `application.properties`.
4. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```
5. Acesse a API no navegador ou via Postman: `http://localhost:8080`

## Licença
Este projeto está licenciado sob a **Apache 2.0**. Para mais informações, acesse [Apache 2.0 License](https://github.com/calebewerneckcouto/CWCDEV.git).

