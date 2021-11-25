package com.ProjetoRabbitMq.config;

public enum QueueDefinition {
	FIRST_QUEUE ("PRODUCT-QUEUE"),
	SECOND_QUEUE ("DELETE-PRODUCT-QUEUE"),
    DIRECT_EXCHANGE ("DIRECT-EXCHANGE"),
    FIRST_BINDING_KEY ("TO-PRODUCT-QUEUE"),
    SECOND_BINDING_KEY ("TO-DELETE-QUEUE"),
    DLQ_EXCHANGE ("DLQ-EXCHANGE"),
    DLQ_QUEUE ("DLQ-QUEUE"),
    DLQ_BINDING_KEY ("TO-DLQ");
	
	private String name;
	private QueueDefinition (String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
