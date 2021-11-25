package com.ProjetoRabbitMq.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Person {

	@Getter @Setter private String name;
	@Getter @Setter private Integer collageCompletedYear;
	@Getter @Setter private LocalDate bornAt;
	@Getter @Setter private Boolean active;
	
}
