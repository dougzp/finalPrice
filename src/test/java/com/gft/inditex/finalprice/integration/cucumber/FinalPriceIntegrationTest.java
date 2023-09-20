package com.gft.inditex.finalprice.integration.cucumber;

import com.gft.inditex.domain.Brands;
import com.gft.inditex.finalprice.infra.adapters.in.PriceDTO;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {WebClientConfig.class})
public class FinalPriceIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private WebClient webClient;
    Mono<PriceDTO> getPrices(String applicationDate, String productId, String brand){

        return

                webClient.get()
                        .uri("/getPrice/" + applicationDate + "/"+productId+ "/"+ Brands.valueOf(brand.toUpperCase()))
                        .exchange()
                        .flatMap(response -> response.bodyToMono(PriceDTO.class));

    }


}
