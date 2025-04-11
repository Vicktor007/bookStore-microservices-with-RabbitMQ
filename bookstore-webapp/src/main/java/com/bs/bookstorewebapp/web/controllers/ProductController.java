package com.bs.bookstorewebapp.web.controllers;

import com.bs.bookstorewebapp.clients.catalogue.CatalogueServiceClient;
import com.bs.bookstorewebapp.clients.catalogue.PageResult;
import com.bs.bookstorewebapp.clients.catalogue.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final CatalogueServiceClient catalogueServiceClient;

    public ProductController(CatalogueServiceClient catalogueServiceClient) {
        this.catalogueServiceClient = catalogueServiceClient;
    }

    @GetMapping
    String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    String showProductsPage(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        model.addAttribute("pageNo", page);
        return "products";
    }

    @GetMapping("/api/products")
    @ResponseBody
    PageResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        log.info("Showing products for page{}", page);
        return catalogueServiceClient.getProducts(page);
    }
}
