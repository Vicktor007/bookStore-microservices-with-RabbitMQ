package com.bs.orderservice.domain.records;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderDeliveredEvent(
        String eventId,
        String orderNumber,
        Set<OrderItem> items,
        Customer customer,
        Address deliveryAddress,
        LocalDateTime createdAt
) {
}
