package com.gft.inditex.finalprice.service;

import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.Price;
import com.gft.inditex.domain.ports.out.RequestPrice;
import com.gft.inditex.persistence.infra.adapters.out.PriceEntity;
import com.gft.inditex.persistence.infra.adapters.out.SpringPricesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;


import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.sql.init.mode=never")
class RequestPriceImplTest {


    private final List<UUID> createdIds = new ArrayList<>();

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private SpringPricesRepository repository;


    @Autowired
    private RequestPrice requestPrice;

    @AfterEach
    void cleanData(){
        repository.deleteAllById(createdIds).block();
    }


    @Test
    void should_return_a_valid_price_just_created() {
        //Given
        UUID id = UUID.randomUUID();
        createdIds.add(id);
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId(id);
        priceEntity.setBrandId(Brands.ZARA.getValue());
        priceEntity.setStartDate(LocalDateTime.now());
        priceEntity.setEndDate(LocalDateTime.now().plusHours(1));
        priceEntity.setPriceListId(1);
        priceEntity.setPriority(1);
        priceEntity.setProductId(1);
        priceEntity.setPrice(100.0);
        priceEntity.setCurrency("USD");
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


        //When
        Price foundPrice = requestPrice.execute(priceEntity.getStartDate().plusMinutes(30), priceEntity.getProductId(), Brands.ZARA).block();

        //Then
        assertThat(foundPrice).isNotNull();
        assertThat(foundPrice.getProductId()).isEqualTo(priceEntity.getProductId());

    }


    @Test
    void should_return_just_one_correct_price() {
        //Given
        UUID id = UUID.randomUUID();
        createdIds.add(id);
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId(id);
        priceEntity.setBrandId(Brands.ZARA.getValue());
        priceEntity.setStartDate(LocalDateTime.now());
        priceEntity.setEndDate(LocalDateTime.now().plusHours(1));
        priceEntity.setPriceListId(1);
        priceEntity.setPriority(1);
        priceEntity.setProductId(1);
        priceEntity.setPrice(50.0);
        priceEntity.setCurrency("USD");
        PriceEntity priceEntity2 = new PriceEntity();
        UUID id2 = UUID.randomUUID();
        createdIds.add(id2);
        priceEntity2.setId(id2);
        priceEntity2.setBrandId(Brands.ZARA.getValue());
        priceEntity2.setStartDate(LocalDateTime.now());
        priceEntity2.setEndDate(LocalDateTime.now().plusHours(1));
        priceEntity2.setPriceListId(1);
        priceEntity2.setPriority(0);
        priceEntity2.setProductId(1);
        priceEntity2.setPrice(100.0);
        priceEntity2.setCurrency("USD");
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

        databaseClient.sql("INSERT INTO prices (id, brand_id, start_date, end_date, price_list_id, priority, product_id, price, currency) VALUES (:id, :brandId, :startDate, :endDate, :priceListId, :priority, :productId, :price, :currency)")
                .bind("id", priceEntity2.getId())
                .bind("brandId", priceEntity2.getBrandId())
                .bind("startDate", priceEntity2.getStartDate())
                .bind("endDate", priceEntity2.getEndDate())
                .bind("priceListId", priceEntity2.getPriceListId())
                .bind("priority", priceEntity2.getPriority())
                .bind("productId", priceEntity2.getProductId())
                .bind("price", priceEntity2.getPrice())
                .bind("currency", priceEntity2.getCurrency())
                .fetch()
                .rowsUpdated().block();

        //When
        Price foundPrice = requestPrice.execute(priceEntity.getStartDate().plusMinutes(30), priceEntity.getProductId(), Brands.ZARA).block();

        //Then
        assertThat(foundPrice).isNotNull();
        assertThat(foundPrice.getPriceValue()).isEqualTo(priceEntity.getPrice());
        assertThat(foundPrice.getProductId()).isEqualTo(priceEntity.getProductId());

    }
}