package com.example.email

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
class EmailApiApplication

fun main(args: Array<String>) {
    runApplication<EmailApiApplication>(*args)
}
