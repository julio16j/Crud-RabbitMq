package com.ProjetoRabbitMq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProjetoRabbitMq.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
