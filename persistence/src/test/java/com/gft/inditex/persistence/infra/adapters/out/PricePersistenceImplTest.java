package com.gft.inditex.persistence.infra.adapters.out;


import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.Price;
import com.gft.inditex.domain.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PricePersistenceImplTest {

    private SpringPricesRepository repository;

    @InjectMocks
    private PricePersistenceImpl adapter;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(SpringPricesRepository.class);
        adapter = new PricePersistenceImpl(repository);
    }


    @Test
    void should_call_service_method_with_correct_params() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2023, 9, 25, 10, 15); // a fixed date-time
        Integer productId = 1;
        PriceEntity mockEntity = new PriceEntity();
        mockEntity.setProductId(productId);
        mockEntity.setBrandId(1);
        mockEntity.setCurrency("EUR");

        // Mocking the repository call
        Mockito.when(repository.findByBrandIdAndApplicationDateAndProductId(
                        Brands.ZARA.getValue(), applicationDate, productId))
                .thenReturn(Mono.just(mockEntity));

        // When
        Mono<Price> resultMono = Mono.fromFuture(() -> adapter.getPricesForZaraBrand(applicationDate, productId, Brands.ZARA));

        // Then
        StepVerifier.create(resultMono)
                .assertNext(result -> {
                    assertNotNull(result);
                    assertEquals(productId, result.getProductId());
                })
                .verifyComplete();

        Mockito.verify(repository, Mockito.times(1)).findByBrandIdAndApplicationDateAndProductId(Brands.ZARA.getValue(), applicationDate, productId);
    }

    @Test
    void should_throw_NotFoundException_when_entity_not_found_in_service() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 56365;

        Mockito.when(repository.findByBrandIdAndApplicationDateAndProductId(Brands.ZARA.getValue(), applicationDate, productId))
                .thenReturn(Mono.empty());

        // When
        CompletableFuture<Price> resultFuture = adapter.getPricesForZaraBrand(applicationDate, productId, Brands.ZARA);

        // Convert CompletableFuture to Mono for StepVerifier
        Mono<Price> resultMono = Mono.fromFuture(() -> resultFuture);

        // Then
        StepVerifier.create(resultMono)
                .expectError(NotFoundException.class)
                .verify();
    }
   }