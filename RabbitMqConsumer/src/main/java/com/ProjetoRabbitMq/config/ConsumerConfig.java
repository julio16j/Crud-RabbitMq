package com.ProjetoRabbitMq.config;

import org.aopalliance.aop.Advice;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ProjetoRabbitMq.service.QueueListener;

@Configuration
public class ConsumerConfig {
	
	@Autowired
	private ConnectionFactory connectionFactory;
	@Autowired
	private QueueListener queueListener;
	
	@Bean
    public MessageListenerContainer listenerContainer () {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("SECOND-QUEUE-ADVANCED");
        container.setMessageListener(queueListener);
        container.setAdviceChain(retryPolicy());
        container.start();
        return container;
    }
	
	private Advice retryPolicy () {
        return RetryInterceptorBuilder
            .stateless()
            .maxAttempts(5)
            .backOffOptions(
                1000, // Initial interval
                2.0, // Multiplier
                6000 // Max interval
            )
            .recoverer(new RejectAndDontRequeueRecoverer())
            .build();
    }
}
