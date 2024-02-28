### Select Language: Portuguese, [English](https://github.com/pedroviniciusz/Task-Manager/README-en.md)

# Sistema de Gerenciamento de Tarefas Baseado em Microserviços

## Visão Geral
Este é um projeto baseado em microsserviços para o gereciamento de tarefas. Utilizando Spring Boot, Config Server, service discovery com o Eureka e rotas com Gateway.

- **Config-Server-Api**: Armazena as configurações dos microsserviços em um [repositório](https://github.com/pedroviniciusz/api-config-repo)
- **Naming-Server-Api**: Utiliza o service discovery
- **Gateway-Api**: Roteia as chamadas para o microsserviço requisitado
- **Auth-Api**: Cria tokens JWT para autenticação
- **Task-Api**: Faz o CRUD das tarefas
- **User-Api**: Faz o CRUD dos usuários

## Pré-requisito
- Java JDK 21 or posterior
- Spring Boot 3.x
- Docker

## Rodando o projeto
1. **Rode o PostgreSQL com o Docker**:
   ```shell
   docker run -p 5432:5432 -v /opt/database/task-manager-db:/var/lib/postgresql/data -e POSTGRES_PASSWORD=1234 -d postgres

Este comando starta o PostgreSQL, que roda por padrão na porta 5432


## Rodando os microsserviços
Cada um deles pode ser iniciado com:

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=dev ou test 
```

É necessário roda-los exatamente nesta ordem:
- ``ConfigServerApi``
- ``NamingServerApi``
- ``GatewayApi``
- ``Qualquer outro já pode ser iniciado após estes três``

## Registros no Eureka
Você pode checar se todos estão registrados no Eureka com:
```
http://localhost:8761/
```

## Microsserviços

### Auth-Api
- **Função**: Gerar tokens JWT.
- **Endpoints**:
  | Path  | Descrição | Verbo |
  | ------------- | ------------- | ------------- |
  |/api/auth/token|Gera e retorna o JWT|POST|

### Task-Api
- **Função**: Realizar o CRUD das tarefas
- **Endpoints**:
  | Path  | Descrição | Verbo |
  | ------------- | ------------- | ------------- |
  |/api/tasks/{id}|Retorna a tarefa correspondente ao ID passado|GET|
  |/api/tasks/user|Retorna as tarefas correspondentes ao usuário logado|GET|
  |/api/tasks|Cria a tarefa|POST|
  |/api/tasks/{id}|Atualiza os dados da tarefa pelo ID|PUT|
  |/api/tasks/{id}|Deleta a tarefa pelo ID (Soft Delete)|DELETE|

  ### User-Api
- **Função**: Realizar o CRUD de usuários
- **Endpoints**:
  | Path  | Descrição | Verbo |
  | ------------- | ------------- | ------------- |
  |/api/users/{id}|Retorna o usuário correspondente ao ID passado|GET|
  |/api/users|Cria o usuário|POST|
  |/api/users/{id}|Atualiza os dados do usuário pelo ID|PUT|
  |/api/users/{id}|Deleta o usuário pelo ID (Soft Delete)|DELETE|


## Documentação
Você pode checar a documentação como: controllers, entidades e etc. Basta escolher qual microsserviço a ser exibido.
```
http://localhost:8765/webjars/swagger-ui/index.html
```

## Status do projeto

O projeto ainda está em desenvolvimento, serão adicionados microsserviços para gerenciar as tarefas, cadastrar usuários, e envio de e-mails.

Além que será implementado lógicas utilizando RabbitMQ, FeignClient, Zipkin, dentre outros.

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)


