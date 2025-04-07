package com.bs.catalogueservice.web.controllers;

import com.bs.catalogueservice.domain.PageResult;
import com.bs.catalogueservice.domain.Product;
import com.bs.catalogueservice.domain.ProductNotFoundException;
import com.bs.catalogueservice.domain.ProductService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
 class ProductController {

    private final ProductService productService;

     ProductController(ProductService productService) {this.productService = productService;}



    @GetMapping
    PageResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1")int pageNo){
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
         sleep();
        return productService.getProductByCode(code)
        .map(ResponseEntity::ok)
                 .orElseThrow(() ->  ProductNotFoundException.forCode(code));
    }

    void sleep(){
         try {
             Thread.sleep(6000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
    }
}

