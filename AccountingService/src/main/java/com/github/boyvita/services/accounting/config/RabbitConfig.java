package com.github.boyvita.services.accounting.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class RabbitConfig {

	@Value("${rabbit.rabbitmq.queueAccount}")
	String accountQueue;

	@Value("${rabbit.rabbitmq.exchange}")
	String exchange;

	@Value("${rabbit.rabbitmq.routingKeyAccount}")
	private String routingkey;

	@Bean
	Queue accountQueue() {
		return new Queue(accountQueue, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(Queue accountQueue, DirectExchange exchange) {
		return BindingBuilder.bind(accountQueue).to(exchange).with(routingkey);
	}


	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

//
//	@Bean
//	public AsyncRabbitTemplate asyncRabbitTemplate(ConnectionFactory connectionFactory) {
//
//		return new AsyncRabbitTemplate((RabbitTemplate) rabbitTemplate(connectionFactory),
//				rpcReplyMessageListenerContainer(connectionFactory),
//				exchange + "/" + routingkey);
//	}




}
