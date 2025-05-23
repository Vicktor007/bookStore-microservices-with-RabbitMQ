package com.bs.orderservice.domain.records;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record OrderRequest(
        @Valid @NotEmpty(message = "Items cannot be empty") Set<OrderItem> items,
        @Valid Customer customer,
        @Valid Address deliveryAddress) {}
