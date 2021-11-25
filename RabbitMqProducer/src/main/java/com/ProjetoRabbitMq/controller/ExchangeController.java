package com.ProjetoRabbitMq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProjetoRabbitMq.model.Person;

@RestController
@RequestMapping("exchanges")
public class ExchangeController {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostMapping("persons/{exchange}/{routingKey}")
    public ResponseEntity<?> postPersonOnExchange(
        @PathVariable String exchange,
        @PathVariable String routingKey,
        @RequestBody Person message){
        System.out.println("sending message $message");
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        return ResponseEntity.ok().build();
    }
}