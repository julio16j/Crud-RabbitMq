package com.ProjetoRabbitMq.service;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.ProjetoRabbitMq.model.Product;
import com.ProjetoRabbitMq.repository.ProductRepository;
import com.rabbitmq.client.Channel;

@Service
public class ProductListener {
	
	@Autowired
	private ProductRepository repository;
	
	@RabbitListener(queues = {"PRODUCT-QUEUE"}, ackMode = "MANUAL", concurrency="5-6")
	public void consumerProductQueue(Product product, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
		if (!isValid(product)) {
            channel.basicNack(tag != null ? tag : 0L, false, false);
        } else {
        	repository.save(product);
            channel.basicAck(tag != null ? tag : 0L, false);
        }
	}
	
	@RabbitListener(queues = {"DELETE-PRODUCT-QUEUE"}, ackMode = "MANUAL", concurrency="5-6")
	public void consumerDeleteQueue(Long id, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException {
		if (!shouldDelete(id)) {
            channel.basicNack(tag != null ? tag : 0L, false, false);
        } else {
        	repository.deleteById(id);
            channel.basicAck(tag != null ? tag : 0L, false);
        }
	}
	
	private boolean shouldDelete(Long id) {
		if (id == null) {
			return false;			
		}
		if (!repository.findById(id).isPresent()) {
			return false;
		}
		return true;
	}

	private boolean isValid(Product product) {
		if (product == null) {
			return false;
		}
		if (product.getName() == null || product.getName().isEmpty()) {
			return false;			
		}
		LocalDate today = LocalDate.now();
		if (product.getExpiryDate() == null || today.isAfter(product.getExpiryDate())) {
			return false;			
		}
		return true;
	}

	
}
