package com.bs.bookstorewebapp.web.controllers;

import com.bs.bookstorewebapp.clients.orders.*;
import com.bs.bookstorewebapp.configurations.SecurityHelper;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderServiceClient orderService;
    private final SecurityHelper securityHelper;

    OrderController(final OrderServiceClient orderService, final SecurityHelper securityHelper) {
        this.orderService = orderService;
        this.securityHelper = securityHelper;
    }

    private Map<String, ?> getHeaders() {
        String accessToken = securityHelper.getAccesToken();
        return Map.of("Authorization", "Bearer " + accessToken);
    }

    @GetMapping("/cart")
    String cart() {
        return "cart";
    }

    @PostMapping("/api/orders")
    @ResponseBody
    OrderConfirmationDTO createOrder(@Valid @RequestBody CreateOrderRequest orderRequest) {
        logger.info("Creating order: {}", orderRequest);
        return orderService.createOrder(getHeaders(), orderRequest);
    }

    @GetMapping("/orders/{orderNumber}")
    String showOrderDetails(@PathVariable String orderNumber, Model model) {
        model.addAttribute("orderNumber", orderNumber);
        return "order_Details";
    }

    @GetMapping("/api/orders/{orderNumber}")
    @ResponseBody
    OrderDTO getOrder(@PathVariable String orderNumber) {
        logger.info("Fetching order details for orderNumber: {}", orderNumber);
        return orderService.getOrder(getHeaders(), orderNumber);
    }

    @GetMapping("/orders")
    String showOrders() {
        return "orders";
    }

    @GetMapping("/api/orders")
    @ResponseBody
    List<OrderSummary> getOrders() {
        logger.info("Fetching orders");
        return orderService.getOrders(getHeaders());
    }
}
