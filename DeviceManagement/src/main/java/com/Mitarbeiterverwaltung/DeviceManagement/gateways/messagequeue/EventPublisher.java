package com.Mitarbeiterverwaltung.DeviceManagement.gateways.messagequeue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class EventPublisher {

    private RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

    }

    public String publishEvent(String exchange, String routingKey, String payload) {

        /*
         * Exchange (hier: lager.events)
         * RoutingKey (hier: artikel.eingetroffen)
         * Queue (hier: lagerqueue_einlagerung)
         * 
         * muss im RabbitMQ konfiguriert sein
         * 
         */

        Object response = rabbitTemplate.convertSendAndReceive(
                exchange,
                routingKey,
                payload);

        System.out.println("!!!MESSAGE SENT!!!! " + payload);

        if (response != null)
            return (String) response;

        return "Response could not be received.";

    }

}
