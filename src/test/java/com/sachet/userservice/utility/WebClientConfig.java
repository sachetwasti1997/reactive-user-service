package com.sachet.userservice.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebTestClient webTestClient(){
        return WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:8081")
                .build();
    }

}
