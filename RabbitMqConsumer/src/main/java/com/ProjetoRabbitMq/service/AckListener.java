package com.ProjetoRabbitMq.service;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.ProjetoRabbitMq.model.Person;
import com.rabbitmq.client.Channel;

@Service
public class AckListener {
	
	@RabbitListener(queues = {"FIRST-QUEUE-ADVANCED"}, ackMode = "MANUAL", concurrency="5-6")
	public void consumerFirstQueue(Person person, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
		System.out.println("Person: " + person);
		if (person.getCollageCompletedYear() == null) {
            System.out.println("message wrong");
            channel.basicNack(tag != null ? tag : 0L, false, false);
        } else {
            System.out.println("message Ok");
            channel.basicAck(tag != null ? tag : 0L, false);
        }
	}

	
}
