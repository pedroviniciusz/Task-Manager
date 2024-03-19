package com.example.user.core.producer;

import com.example.user.web.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbit.exchange.name}")
    private String exchange;

    @Value("${rabbit.routing.key}")
    private String routingKey;

    public void sendMessage(@Payload EmailDto emailDto) {
        log.info(String.format("Sending e-mail to -> %s ", emailDto.to()));
        rabbitTemplate.convertAndSend(exchange, routingKey, emailDto);
    }

}
