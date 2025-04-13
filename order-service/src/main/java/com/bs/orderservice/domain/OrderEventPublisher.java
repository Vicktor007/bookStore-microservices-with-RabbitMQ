package com.bs.orderservice.domain;

import com.bs.orderservice.ApplicationProperties;
import com.bs.orderservice.domain.records.OrderCancelledEvent;
import com.bs.orderservice.domain.records.OrderCreatedEvent;
import com.bs.orderservice.domain.records.OrderDeliveredEvent;
import com.bs.orderservice.domain.records.OrderErrorEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties applicationProperties;

    OrderEventPublisher(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationProperties = applicationProperties;
    }

    public void publish(OrderCreatedEvent event) {
        this.send(applicationProperties.newOrdersQueue(), event);
    }

    public void publish(OrderDeliveredEvent event) {
        this.send(applicationProperties.deliveredOrdersQueue(), event);
    }

    public void publish(OrderCancelledEvent event) {
        this.send(applicationProperties.cancelledOrdersQueue(), event);
    }

    public void publish(OrderErrorEvent event) {
        this.send(applicationProperties.errorOrdersQueue(), event);
    }

    private void send(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(applicationProperties.orderEventsExchange(), routingKey, payload);
    }
}
