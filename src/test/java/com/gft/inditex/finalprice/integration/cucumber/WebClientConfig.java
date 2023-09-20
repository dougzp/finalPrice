package com.gft.inditex.finalprice.integration.cucumber;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.reactive.function.client.WebClient;

@Lazy
@TestConfiguration
public class WebClientConfig {

    @Value("${server.servlet.context-path:}")
    private String contextPath;


    @Bean
    public WebClient webClient(@Value("${local.server.port}") int port) {
        return WebClient.builder()
                .baseUrl("http://localhost:" + port + contextPath)
                .build();
    }
}