### Select Language: [Portuguese](https://github.com/pedroviniciusz/Task-Manager/blob/master/README.md), English

# Microservices-Based Task Management System

## Overview
This project is a microservice-based system for tasks management. It's using Spring Boot, Config Server, Service Discovery with Eureka and routing with Gateway.

- **Config-Server-Api**: Stores microservices configurations in a [repository](https://github.com/pedroviniciusz/api-config-repo)
- **Naming-Server-Api**: Uses service discovery
- **Gateway-Api**: Routing and decoding the JWT to authorize or deny requests to the requested microservice
- **Auth-Api**: Generates JWT Tokens for authetication and authorization
- **Task-Api**: Tasks CRUD
- **User-Api**: Users CRUD
- **Email-Api**: Sends e-mails when registering a user (will also send tasks that are close to the deadline later).

## Prerequisites
- Java JDK 21 or later
- Maven
- Docker

## Starting up the docker
**Start the containers**:
   ```shell
   docker-compose up
   ```

This command starts the PostgreSQL and Zipkin. You can check out at [docker-compose.yml](https://github.com/pedroviniciusz/Task-Manager/blob/master/docker-compose.yml)


## Starting up the microservices
Each microservice can be started using:

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=dev or test 
```

It's necessary to run them exactly in this order:
1. ``ConfigServerApi``
2. ``NamingServerApi``
3. ``GatewayApi``
4. ``Anyone can be started after``

## Eureka registration
You can check out if the microservices are registered at:
```shell
http://localhost:8761/
```
![Print Eureka](https://github.com/pedroviniciusz/Task-Manager/assets/86628590/517a306e-da9c-40b2-82f1-24b5c4729688)


## Zipkin for tracing
You can go through the requests tracing at:
```shell
http://localhost:9411/zipkin/
```
![Print Zipkin](https://github.com/pedroviniciusz/Task-Manager/assets/86628590/9fbe0eb5-d079-4c64-8ae9-a144f05bafde)

## RabbitMQ
RabbitMQ interface is avaliable at: <br />
User: Guest <br />
Password: Guest
```shell
http://localhost:15672/
```
![Print RabbitMQ](https://github.com/pedroviniciusz/Task-Manager/assets/86628590/4bd65f48-16a3-47a6-975e-143228d3971b)


## Swagger
You can check the microsservices documentation out like: controllers and entities. Just select which microservice you want to at:
```shell
http://localhost:8765/swagger-ui.html
```
![Print do swagger](https://github.com/pedroviniciusz/Task-Manager/assets/86628590/7b0d63bc-f0e3-4dde-b597-b0bd2e56ad9f)


## Project status

The project is still under development. More microservices will be added for managing tasks, sendind e-mails and etc.


Additionally, will be implemented more features using RabbitMQ, FeignClient, Zipkin, among others.

![development](http://img.shields.io/static/v1?label=STATUS&message=UNDER%20DEVELOPMENT&color=GREEN&style=for-the-badge)