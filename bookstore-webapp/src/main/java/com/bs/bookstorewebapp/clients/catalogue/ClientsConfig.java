package com.bs.bookstorewebapp.clients.catalogue;

import com.bs.bookstorewebapp.ApplicationProperties;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class ClientsConfig {

    private final ApplicationProperties applicationProperties;

     ClientsConfig(ApplicationProperties applicationProperties) {
         this.applicationProperties = applicationProperties;
     }

    @Bean
    RestClientCustomizer restClientCustomizer() {
        return restClientBuilder -> restClientBuilder
                .baseUrl(applicationProperties.apiGatewayUrl())
                .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofSeconds(5))
                        .withReadTimeout(Duration.ofSeconds(5))));
    }

    @Bean
    CatalogueServiceClient catalogueServiceClient(RestClient.Builder clientBuilder) {
         RestClient restClient = clientBuilder.build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(CatalogueServiceClient.class);
    }

}
