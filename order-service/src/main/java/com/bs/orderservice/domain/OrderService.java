package com.bs.orderservice.domain;

import com.bs.orderservice.domain.records.OrderRequest;
import com.bs.orderservice.domain.records.OrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;

    OrderService(OrderRepository orderRepository, OrderValidator orderValidator) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
    }

    public OrderResponse createOrder(String userName, OrderRequest orderRequest) {
        orderValidator.validate(orderRequest);
        OrderEntity newOrder = OrderMapper.convertToEntity(orderRequest);
        newOrder.setUserName(userName);
        OrderEntity order = orderRepository.save(newOrder);
        log.info("created order with orderNumber = {}", order.getOrderNumber());
        return new OrderResponse(order.getOrderNumber());

    }
}
