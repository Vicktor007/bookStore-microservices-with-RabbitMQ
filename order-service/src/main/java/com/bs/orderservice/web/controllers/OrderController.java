package com.bs.orderservice.web.controllers;

import com.bs.orderservice.domain.OrderNotFoundException;
import com.bs.orderservice.domain.OrderService;
import com.bs.orderservice.domain.SecurityService;
import com.bs.orderservice.domain.records.OrderDTO;
import com.bs.orderservice.domain.records.OrderRequest;
import com.bs.orderservice.domain.records.OrderResponse;
import com.bs.orderservice.domain.records.OrderSummary;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "security_auth")
class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
 private final SecurityService securityService;

 OrderController(OrderService orderService, SecurityService securityService) {
     this.orderService = orderService;
     this.securityService = securityService;
 }

 @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
 OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
     String userName = securityService.getLoginUserName();
     log.info(userName);
     return orderService.createOrder(userName, orderRequest);
 }

 @GetMapping
    List<OrderSummary> getOrders() {
     String userName = securityService.getLoginUserName();
     log.info(userName);
     return orderService.findOrders(userName);
 }

 @GetMapping(value = "/{orderNumber}")
    OrderDTO getOrder(@PathVariable String orderNumber) {
     String userName = securityService.getLoginUserName();
     return orderService.findUserOrder(userName, orderNumber)
             .orElseThrow(() -> new OrderNotFoundException(orderNumber));
 }
}
