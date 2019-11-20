package com.github.boyvita.services.payment.controller;

import com.github.boyvita.services.model.Item;
import com.github.boyvita.services.model.Order;
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


	public void send(Order order) {
		rabbitTemplate.convertAndSend(exchange, routingKeyAccount, order);
		rabbitTemplate.convertAndSend(exchange, routingKeyCatalog, order);
		System.out.println("Send msg = " + order);
	}
}