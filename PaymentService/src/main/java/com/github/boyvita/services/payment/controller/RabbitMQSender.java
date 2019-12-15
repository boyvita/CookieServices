package com.github.boyvita.services.payment.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQSender {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${rabbit.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${rabbit.rabbitmq.routingKeyAccount}")
	private String routingKeyAccount;

	@Value("${rabbit.rabbitmq.routingKeyCatalog}")
	private String routingKeyCatalog;


	public void send(Long orderId) {
		rabbitTemplate.convertAndSend(exchange, routingKeyAccount, orderId);
		rabbitTemplate.convertAndSend(exchange, routingKeyCatalog, orderId);
		System.out.println("Send msg = " + orderId);
	}
}