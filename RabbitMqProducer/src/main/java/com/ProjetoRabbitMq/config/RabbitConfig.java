package com.ProjetoRabbitMq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

@Configuration
public class RabbitConfig {
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	@PostConstruct
	public void createRabbitElements () {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        createExchange(rabbitAdmin);
        createDLQ(rabbitAdmin);
        createFirstQueue(rabbitAdmin);
        createSecondQueue(rabbitAdmin);
	}

	private void createDLQ(RabbitAdmin rabbitAdmin) {
		Queue queue = QueueBuilder.durable(QueueDefinition.DLQ_QUEUE.getName())
		        .build();
	    Exchange exchange = ExchangeBuilder
	        .directExchange(QueueDefinition.DLQ_EXCHANGE.getName())
	        .durable(true).build();
	     Binding binding = new Binding(
	        QueueDefinition.DLQ_QUEUE.getName(),
	        Binding.DestinationType.QUEUE,
	        QueueDefinition.DLQ_EXCHANGE.getName(),
	        QueueDefinition.DLQ_BINDING_KEY.getName(),
	        null
	    );
		rabbitAdmin.declareQueue(queue);
		rabbitAdmin.declareExchange(exchange);
		rabbitAdmin.declareBinding(binding);
	}

	private void createSecondQueue(RabbitAdmin rabbitAdmin) {
		Queue queue = QueueBuilder.durable(QueueDefinition.SECOND_QUEUE.getName())
				.maxLength(10)
				.ttl(300_000)
				.deadLetterExchange(QueueDefinition.DLQ_EXCHANGE.getName())
				.deadLetterRoutingKey(QueueDefinition.DLQ_BINDING_KEY.getName())
	            .build();
		Binding binding = new Binding(
		        QueueDefinition.SECOND_QUEUE.getName(),
		        Binding.DestinationType.QUEUE,
		        QueueDefinition.DIRECT_EXCHANGE.getName(),
		        QueueDefinition.SECOND_BINDING_KEY.getName(),
		        null);
       rabbitAdmin.declareQueue(queue);
       rabbitAdmin.declareBinding(binding);
		
	}

	private void createFirstQueue(RabbitAdmin rabbitAdmin) {
		Queue queue = QueueBuilder.durable(QueueDefinition.FIRST_QUEUE.getName())
            .deadLetterExchange(QueueDefinition.DLQ_EXCHANGE.getName())
            .deadLetterRoutingKey(QueueDefinition.DLQ_BINDING_KEY.getName())
            .build();
        Binding binding = new Binding(
            QueueDefinition.FIRST_QUEUE.getName(),
            Binding.DestinationType.QUEUE,
            QueueDefinition.DIRECT_EXCHANGE.getName(),
            QueueDefinition.FIRST_BINDING_KEY.getName(),
            null
        );
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
		
	}

	private void createExchange(RabbitAdmin rabbitAdmin) {
		Exchange exchange = ExchangeBuilder
            .directExchange(QueueDefinition.DIRECT_EXCHANGE.getName())
            .durable(true)
            .build();
        rabbitAdmin.declareExchange(exchange);
		
	}
}
