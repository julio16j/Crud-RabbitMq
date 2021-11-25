package com.ProjetoRabbitMq.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProjetoRabbitMq.model.Person;

@Service
public class QueueListener implements MessageListener  {
	
	@Autowired
	private MessageConverter messageConverter;
	
	@Override
	public void onMessage(Message message) {
		System.out.println("receive message from " + message.getMessageProperties().getConsumerQueue());
        Person person = (Person) messageConverter.fromMessage(message); 
        System.out.println("body " + person);
        if (person.getCollageCompletedYear() == null) {
        	throw new RuntimeException("Wrong data for person class");
        }
	}

	
}
