### Select Language: Portuguese, [English](https://github.com/pedroviniciusz/Task-Manager/README-en.md)

# Sistema de Gerenciamento de Tarefas Baseado em Microserviços

## Visão Geral
Este é um projeto baseado em microsserviços para o gereciamento de tarefas. Utilizando Spring Boot, Config Server, service discovery com o Eureka e rotas com Gateway.

- **Config-Server**: Armazena as configurações dos microsserviços
- **Api-Gateway**: Roteia as chamadas para o microsserviço requisitado
- **Naming-Server**: Utiliza o service discovery
- **Api-Auth**: Cria tokens JWT para autenticação
- **Api-Task**: Faz o CRUD das tarefas

## Pré-requisito
- Java JDK 21 or later
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
- ``ApiConfigServer``
- ``ApiNamingServer``
- ``ApiGateway``
- ``Qualquer outro já pode ser iniciado após estes três``

## Registros no Eureka
Você pode checar se todos estão registrados no Eureka com:
```
http://localhost:8761/
```

## Microsserviços

### Api-Auth
- **Função**: Gerar tokens JWT.
- **Endpoints**:
  | Path  | Descrição | Verbo |
  | ------------- | ------------- | ------------- |
  |/api/auth/token|Gera e retorna o JWT|POST|

### Api-Task
- **Função**: Realizar o CRUD das tarefas
- **Endpoints**:
  | Path  | Descrição | Verbo |
  | ------------- | ------------- | ------------- |
  |/api/task/{id}|Retorna a tarefa correspondente ao ID passado|GET|
  |/api/task/user|Retorna as tarefas correspondentes ao usuário logado|GET|
  |/api/task|Cria a tarefa|POST|
  |/api/task/{id}|Atualiza os dados da tarefa pelo ID|PUT|
  |/api/task/{id}|Deleta a tarefa pelo ID|DELETE|


## Documentação
Você pode checar a documentção como: controllers, entidades e etc. Basta escolher qual microsserviço a ser exibido.
```
http://localhost:8765/webjars/swagger-ui/index.html
```

## Status do projeto

O projeto ainda está em desenvolvimento, serão adicionados microsserviços para gerenciar as tarefas, cadastrar usuários, e envio de e-mails.

Além que será implementado lógicas utilizando RabbitMQ, FeignClient, Zipkin, detre outros.

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)


