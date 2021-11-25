package com.ProjetoRabbitMq.service;

import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ProjetoRabbitMq.config.QueueDefinition;
import com.ProjetoRabbitMq.model.Product;
import com.ProjetoRabbitMq.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private RabbitTemplate rabbitService;
	
	@Autowired
	private ProductRepository repository;
	
	public List<Product> getFiltering(Product filtroProduto) {
		Product produto = filtroProduto == null ? new Product() : filtroProduto;
		return repository.findAll(Example.of(produto));
	}

	public Product getById(Long id) {
		Optional<Product> produtoEncontrado = repository.findById(id);
		if (!produtoEncontrado.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product: " + id + " not found");
		}
		return produtoEncontrado.get();
	}

	public void postProduct(Product product) {
		rabbitService.convertAndSend(QueueDefinition.DIRECT_EXCHANGE.getName(),
				QueueDefinition.FIRST_BINDING_KEY.getName(), product);
	}
	
	public void deleteProduct(Long id) {
		rabbitService.convertAndSend(QueueDefinition.DIRECT_EXCHANGE.getName(),
				QueueDefinition.SECOND_BINDING_KEY.getName(), id);
	}

}
