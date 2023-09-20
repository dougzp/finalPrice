package com.gft.inditex.finalprice.infra.in;

import com.gft.inditex.domain.Price;
import com.gft.inditex.finalprice.FinalPriceApplication;
import com.gft.inditex.finalprice.application.ports.out.RequestPrice;
import com.gft.inditex.finalprice.infra.adapters.in.PriceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = FinalPriceApplication.class,properties = {
        "spring.datasource.url=",
        "spring.jpa.hibernate.ddl-auto=none"
})
class PriceHandlerTest {


    @Autowired
    private WebTestClient webClient;

    @MockBean
    private RequestPrice requestPrice;

    @Test
    void should_return_ok_for_a_valid_request() {
        LocalDateTime date = LocalDateTime.now();
        when(requestPrice.execute(any(LocalDateTime.class), anyInt(), any()))
                .thenReturn(Mono.just(Price.builder().build()));

        webClient.get()
                .uri("/getPrice/" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss")) + "/123/ZARA")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PriceDTO.class);
    }

    @Test
    void should_return_error_for_a_invalid_date() {
        webClient.get()
                .uri("/getPrice/invalid_date/123/ZARA")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Internal Server Error");
    }
    @Test
    void should_return_error_for_a_invalid_productId() {
        LocalDateTime date = LocalDateTime.now();

        webClient.get()
                .uri("/getPrice/" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss")) + "/invalid_id/ZARA")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Internal Server Error");

    }

}