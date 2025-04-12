package com.bs.catalogueservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPI3Configuration {

    @Value("${swagger.api-gateway-url}")
    String swaggerGatewayUrl;

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Catalogue Service APIs")
                        .description("Bookstore Catalogue Service APIs")
                        .version("v1.0")
                        .contact(new Contact().name("Victor Olayiwola").email("vicktord007@gmail.com")))
                .servers(List.of(new Server().url(swaggerGatewayUrl)));
    }

}
