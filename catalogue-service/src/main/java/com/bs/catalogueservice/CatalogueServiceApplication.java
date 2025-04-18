package com.bs.catalogueservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CatalogueServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogueServiceApplication.class, args);
    }
}
