package com.gft.inditex.finalprice.infra.in;

import com.gft.inditex.domain.Price;
import com.gft.inditex.finalprice.FinalPriceApplication;
import com.gft.inditex.domain.ports.out.RequestPrice;
import com.gft.inditex.finalprice.config.TestConfig;
import com.gft.inditex.finalprice.infra.adapters.in.PriceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.AutoConfigureDataR2dbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {FinalPriceApplication.class, TestConfig.class}, properties = {
        "spring.sql.init.mode=never",
        "spring.r2dbc.initialization-mode=never"
})
@AutoConfigureDataR2dbc
@Import(TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PriceHandlerTest {


    @Autowired
    private WebTestClient webClient;

    @MockBean
    private RequestPrice requestPrice;

    @Test
    void should_return_ok_for_a_valid_request() {
        LocalDateTime date = LocalDateTime.now();
        when(requestPrice.execute(any(LocalDateTime.class), anyInt(), any()))
                .thenReturn(CompletableFuture.completedFuture(Price.builder().build()));

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
                .expectBody(String.class).isEqualTo("Invalid date format. Please use 'yyyy-MM-dd-HH.mm.ss'.");
    }
    @Test
    void should_return_error_for_a_invalid_productId() {
        LocalDateTime date = LocalDateTime.now();

        webClient.get()
                .uri("/getPrice/" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss")) + "/invalid_id/ZARA")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(String.class).isEqualTo("Invalid product ID format. Please provide a valid number.");

    }

}