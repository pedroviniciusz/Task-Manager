package com.example.email.service

import com.example.email.dto.EmailDto
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class EmailService(private val javaMailSender: JavaMailSender) {

    @RabbitListener(queues = ["email-sender"])
    fun sendEmail(@Payload emailDto: EmailDto) {
        val message = SimpleMailMessage()
        message.setTo(emailDto.to)
        message.setSubject(emailDto.subject)
        message.setText(emailDto.body)

        javaMailSender.send(message)
    }

}