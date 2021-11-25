package com.ProjetoRabbitMq.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter private Long id;
	
	@Getter @Setter private String name;
	@Getter @Setter private String description;
	@Getter @Setter private LocalDate producedAt;
	@Getter @Setter private LocalDate expiryDate;
	@Getter @Setter private Float price;
	
}
