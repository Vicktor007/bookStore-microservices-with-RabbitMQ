package com.bs.bookstorewebapp.clients.catalogue;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface CatalogueServiceClient {

    @GetExchange("/catalogue/api/products")
    PageResult<Product> getProducts(@RequestParam int page);

    @GetExchange("/catalogue/api/products/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code);
}
