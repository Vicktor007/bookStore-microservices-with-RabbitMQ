package com.bs.orderservice.domain;

import com.bs.orderservice.clients.catalogue.Product;
import com.bs.orderservice.clients.catalogue.ProductServiceClient;
import com.bs.orderservice.domain.records.OrderItem;
import com.bs.orderservice.domain.records.OrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OrderValidator {
    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);

    private final ProductServiceClient productServiceClient;

    OrderValidator(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    void validate(OrderRequest orderRequest) {
        Set<OrderItem> items = orderRequest.items();
        for (OrderItem item : items) {
            Product product = productServiceClient.getProductByCode(item.code())
                    .orElseThrow(() -> new InvalidOrderException("Product with code " + item.code() + " not found"));
            if (item.price().compareTo(product.price()) != 0){
                log.error("Product price not matching actual price {}, received price:{}", product.price(), item.price());
                throw new InvalidOrderException("Product price not matching actual price");
            }
        }
    }
}
