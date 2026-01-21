package com.Mitarbeiterverwaltung.HrManagement.gateways.messagequeue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String publishEvent(String exchange, String routingKey, String payload) {
        Object response = rabbitTemplate.convertSendAndReceive(exchange, routingKey, payload);
        if (response != null) {
            return response.toString();
        }
        return "Response could not be received.";
    }
}
