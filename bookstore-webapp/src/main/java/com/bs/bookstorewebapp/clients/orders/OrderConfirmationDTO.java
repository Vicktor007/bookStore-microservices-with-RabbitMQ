package com.bs.bookstorewebapp.clients.orders;

public record OrderConfirmationDTO(String orderNumber, OrderStatus status) {}
