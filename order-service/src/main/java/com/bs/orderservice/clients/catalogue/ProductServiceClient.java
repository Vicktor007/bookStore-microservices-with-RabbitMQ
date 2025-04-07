package com.bs.orderservice.clients.catalogue;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class ProductServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceClient.class);

    private final RestClient restClient;

    ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @CircuitBreaker(name = "catalogue-service")
    @Retry(name = "catalogue-service", fallbackMethod = "getProductByCodeFallback")
    public Optional<Product> getProductByCode(String code) {
        logger.info("Fetching product by code: {}", code);

            var product = restClient
                    .get()
                    .uri("/api/products/{code}", code)
                    .retrieve()
                    .body(Product.class);
            return Optional.ofNullable(product);
    }

    Optional<Product> getProductByCodeFallback(String code, Throwable throwable) {
        System.out.println("Fallback method: " + code);
        return Optional.empty();
    }
}
