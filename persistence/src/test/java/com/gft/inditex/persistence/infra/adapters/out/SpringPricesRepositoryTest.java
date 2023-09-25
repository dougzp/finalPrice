package com.gft.inditex.persistence.infra.adapters.out;

import com.gft.inditex.domain.Brands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.*;


@EnableAutoConfiguration
@ContextConfiguration(classes = SpringPricesRepository.class)
@DataR2dbcTest
class SpringPricesRepositoryTest {

    private final List<UUID> createdIds = new ArrayList<>();

    @Autowired
    private DatabaseClient databaseClient;
    @Autowired
    private SpringPricesRepository repository;


    @AfterEach
    void cleanData(){
       repository.deleteAllById(createdIds).block();
    }
    @Test
    void should_find_a_correct_price() {
        // Given

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId(UUID.randomUUID());
        priceEntity.setBrandId(Brands.ZARA.getValue());
        priceEntity.setStartDate(LocalDateTime.now());
        priceEntity.setEndDate(LocalDateTime.now().plusHours(1));
        priceEntity.setPriceListId(1);
        priceEntity.setPriority(1);
        priceEntity.setProductId(1);
        priceEntity.setPrice(100.0);
        priceEntity.setCurrency("USD");
        createdIds.add(priceEntity.getId());
        databaseClient.sql("INSERT INTO prices (id, brand_id, start_date, end_date, price_list_id, priority, product_id, price, currency) VALUES (:id, :brandId, :startDate, :endDate, :priceListId, :priority, :productId, :price, :currency)")
                    .bind("id", priceEntity.getId())
                    .bind("brandId", priceEntity.getBrandId())
                    .bind("startDate", priceEntity.getStartDate())
                    .bind("endDate", priceEntity.getEndDate())
                    .bind("priceListId", priceEntity.getPriceListId())
                    .bind("priority", priceEntity.getPriority())
                    .bind("productId", priceEntity.getProductId())
                    .bind("price", priceEntity.getPrice())
                    .bind("currency", priceEntity.getCurrency())
                    .fetch()
                    .rowsUpdated().block();

        // When

        Mono<PriceEntity> result = repository.findByBrandIdAndApplicationDateAndProductId(
                priceEntity.getBrandId(),
                LocalDateTime.now(),
                priceEntity.getProductId()
        );

        StepVerifier.create(result)
                .expectNextMatches(entity ->
                                entity.getBrandId().equals(priceEntity.getBrandId()) &&
                                        entity.getProductId().equals(priceEntity.getProductId())

                )
                .verifyComplete();
    }


}