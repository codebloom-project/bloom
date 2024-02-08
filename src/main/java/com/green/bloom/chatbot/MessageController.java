package com.green.bloom.chatbot;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller // /message/**
public class MessageController {

	private final RabbitTemplate template;
	
	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.template.routing-key}")
	private String routingKey;
	
	// /message/bot
	@MessageMapping("/bot")
	public void bot(Question dto) {
		template.convertAndSend(exchange, routingKey, dto);
	}
}
