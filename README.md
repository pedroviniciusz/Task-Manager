### Select Language: Portuguese, [English](https://github.com/pedroviniciusz/Task-Manager/blob/master/README-en.md)

# Sistema de Gerenciamento de Tarefas Baseado em Microserviços

## Visão Geral
Este é um projeto baseado em microsserviços para o gereciamento de tarefas. Utilizando Spring Boot, Config Server, service discovery com o Eureka e rotas com Gateway.

- **Config-Server-Api**: Armazena as configurações dos microsserviços em um [repositório](https://github.com/pedroviniciusz/api-config-repo)
- **Naming-Server-Api**: Utiliza o service discovery
- **Gateway-Api**: Roteia e decodifica o JWT para autorizar ou não as chamadas para o microsserviço requisitado
- **Auth-Api**: Cria tokens JWT para autenticação e autorização
- **Task-Api**: Faz o CRUD das tarefas
- **User-Api**: Faz o CRUD dos usuários

## Pré-requisito
- Java JDK 21 ou posterior
- Spring Boot 3.x
- Docker

## Rodando o projeto
**Inicie os containers com o comando**:
   ```shell
   docker-compose up
   ```

Este comando starta o PostgreSQL e Zipkin. Você pode conferir em [docker-compose.yml](https://github.com/pedroviniciusz/Task-Manager/blob/master/docker-compose.yml)


## Rodando os microsserviços
Cada um deles pode ser iniciado com:

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=dev ou test 
```

É necessário roda-los exatamente nesta ordem:
1. ``ConfigServerApi``
2. ``NamingServerApi``
3. ``GatewayApi``
4. ``Qualquer outro já pode ser iniciado após estes três``

## Registros no Eureka
Você pode checar se todos estão registrados no Eureka com:
```shell
http://localhost:8761/
```
![Print do Eureka](https://github.com/pedroviniciusz/Task-Manager/assets/86628590/517a306e-da9c-40b2-82f1-24b5c4729688)


## Tracing com Zipkin
Você pode checar o tracing das requisições em:
```shell
http://localhost:9411/zipkin/
```
![Print do Zipkin](https://github.com/pedroviniciusz/Task-Manager/assets/86628590/9fbe0eb5-d079-4c64-8ae9-a144f05bafde)


## Swagger
Você pode checar a documentação como: controllers, entidades e etc. Basta escolher qual microsserviço a ser exibido em "Select a definition" em:
```shell
http://localhost:8765/swagger-ui.html
```
![Print do swagger](https://github.com/pedroviniciusz/Task-Manager/assets/86628590/7b0d63bc-f0e3-4dde-b597-b0bd2e56ad9f)


## Status do projeto

O projeto ainda está em desenvolvimento, serão adicionados microsserviços para gerenciar as tarefas, cadastrar usuários, e envio de e-mails.

Além que será implementado lógicas utilizando RabbitMQ, FeignClient, Zipkin, dentre outros.

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)