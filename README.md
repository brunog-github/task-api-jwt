# Task API with JWT

Task API é uma aplicação desenvolvida em Kotlin usando Ktor, PostgreSQL e autenticação JWT. Esta API permite gerenciar tarefas com funcionalidades como criação, leitura, atualização e exclusão de tarefas, além de suportar autenticação de usuários.

## Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação usada para desenvolver a aplicação.
- **Ktor**: Framework para construção de aplicativos web e APIs.
- **PostgreSQL**: Banco de dados relacional utilizado para armazenar as informações.
- **Exposed**: Exposed é uma biblioteca SQL leve baseada em um driver JDBC para a linguagem Kotlin.
- **Flyway**: Flyway é uma ferramenta de migração de banco de dados baseada em código aberto.
- **JWT (JSON Web Tokens)**: Método utilizado para autenticação e autorização de usuários.
- **Docker**: Ferramenta para containerização dos serviços.
- **Swagger**: Ferramenta para documentação e testes da API.

## Funcionalidades

- **Autenticação de Usuários**: Cadastro e login de usuários utilizando JWT para autenticação.
- **Gerenciamento de Tarefas**: Endpoints para criar, ler, atualizar e excluir tarefas.
- **Relações Entre Usuários e Tarefas**: Cada usuário pode gerenciar suas próprias tarefas.

## Instalação

### Pré-requisitos

- JDK 17 ou superior
- PostgreSQL
- Gradle SDK ou Intellij IDEA
- Docker e Docker Compose

### Passos para Instalação

1. Clone o repositório:
    ```sh
    git clone https://github.com/brunog-github/task-api-jwt.git
    cd task-api-jwt
    ```

2. Configure o banco de dados PostgreSQL usando Docker Compose:
   - Crie / Edite um arquivo `docker-compose.yml` na raiz do projeto com o seguinte conteúdo:

    ```yaml
    version: '3.8'

    services:
      task-app-db:
        image: bitnami/postgresql
        ports:
          - "5432:5432"
        environment:
          - POSTGRESQL_USERNAME=docker
          - POSTGRESQL_PASSWORD=docker
          - POSTGRESQL_DATABASE=taskdb
        volumes:
          - pgdata:/bitnami/postgresql

    volumes:
      pgdata:
        driver: local
    ```

   - Verifique se a `containers` na porta 5432.
   
   - Execute o Docker Compose:
    ```sh
    docker-compose up -d
    ```

3. Atualize as dependências e compile o projeto:
    ```sh
    ./gradlew build
    ```

4. Execute a aplicação:
    ```sh
    ./gradlew run
    ```

## Documentação da API
A documentação completa da API, incluindo a capacidade de testar os endpoints, está disponível através do Swagger. Acesse o Swagger no seguinte endereço:
```sh
http://localhost:8080/swagger
```

## Configuração

Edite o arquivo `src/main/resources/application.conf` para configurar as informações do banco de dados e outras configurações:

```hocon
ktor {
    deployment {
        port = 8080
        port = ${?PORT}
    }
    application {
        modules = [ com.example.ApplicationKt.module ]
    }
}

database {
    driver = "org.postgresql.Driver"
    url = "jdbc:postgresql://localhost:5432/taskdb"
    user = "your-db-user"
    password = "your-db-password"
}

jwt {
    secret = "your-secret-key"
    issuer = "your-issuer"
    audience = "your-audience"
    realm = "your-realm"
}
```