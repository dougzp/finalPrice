package com.gft.inditex.persistence.infra.adapters.out;


import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;


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
        //Given
        LocalDateTime applicationDate = LocalDateTime.now();
        Integer productId = 1;
        PriceEntity mockEntity = new PriceEntity();
        mockEntity.setProductId(productId);
        mockEntity.setBrandId(1);
        mockEntity.setCurrency("EUR");
        Mockito.when(repository.findByBrandIdAndApplicationDateAndProductId(Brands.ZARA.getValue(), applicationDate, productId))
                .thenReturn(Mono.just(mockEntity));

        // When
        Mono<Price> resultMono = adapter.getPricesForZaraBrand(applicationDate, productId, Brands.ZARA);

        // Then
        StepVerifier.create(resultMono)
                .assertNext(result -> {
                    assertNotNull(result);
                    assertEquals(productId, result.getProductId());
                })
                .verifyComplete();

        Mockito.verify(repository, Mockito.times(1)).findByBrandIdAndApplicationDateAndProductId(Brands.ZARA.getValue(), applicationDate, productId);

    }
}