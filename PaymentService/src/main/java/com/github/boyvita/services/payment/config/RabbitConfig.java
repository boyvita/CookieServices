package com.github.boyvita.services.payment.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfig {

	@Value("${rabbit.rabbitmq.queueAccount}")
	String accountQueue;


	@Value("${rabbit.rabbitmq.queueCatalog}")
	String catalogQueue;


	@Value("${rabbit.rabbitmq.exchange}")
    String exchange;

	@Value("${rabbit.rabbitmq.routingKeyAccount}")
	private String accountingKey;


	@Value("${rabbit.rabbitmq.routingKeyCatalog}")
	private String catalogKey;

	@Bean
	Queue accQueue() {
		return new Queue(accountQueue, false);
	}

	@Bean
	Queue catalogQueue() {
		return new Queue(catalogQueue, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding accountServiceBinding(Queue accQueue, DirectExchange exchange) {
		return BindingBuilder.bind(accQueue).to(exchange).with(accountingKey);
	}

	@Bean
	Binding catalogServiceBinding(Queue catalogQueue, DirectExchange exchange) {
		return BindingBuilder.bind(catalogQueue).to(exchange).with(catalogKey);
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
//				exchange + "/" + accountingKey);
//	}




}
