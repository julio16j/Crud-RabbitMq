package com.ProjetoRabbitMq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProjetoRabbitMq.model.Product;
import com.ProjetoRabbitMq.service.ProductService;

@RestController
@RequestMapping("product")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping
    public ResponseEntity<List<Product>> get(
        Product productFilter){
        return ResponseEntity.ok(service.getFiltering(productFilter));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Product> getById(
        @PathVariable("id") Long id){
        return ResponseEntity.ok(service.getById(id));
    }
	
	@PostMapping
	public ResponseEntity<String> save (
			@RequestBody Product product) {
		service.postProduct(product);
		return ResponseEntity.ok("Product successfully forwarded to creation queue");
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete (
			@PathVariable("id") Long id) {
		service.deleteProduct(id);
		return ResponseEntity.ok("Product successfully forwarded to delete queue");
	}
}