package com.ProjetoRabbitMq;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class ProjetoRabbitMqApplication {
	
	@Bean
    public MessageConverter messageConverter ( ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ProjetoRabbitMqApplication.class, args);
	}

}
