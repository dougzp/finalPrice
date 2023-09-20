package com.gft.inditex.persistence.infra.adapters.out;


import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceMapperTest {
    private PriceMapper priceMapper;

    @BeforeEach
    public void setUp() {
        priceMapper = new PriceMapper();
    }
    @Test
    void should_map_from_entity_to_domain() {
        // Given
        PriceEntity entity = new PriceEntity();
        entity.setBrandId(Brands.ZARA.getValue());
        entity.setStartDate(LocalDateTime.now());
        entity.setEndDate(LocalDateTime.now().plusHours(1));
        entity.setPriceListId(1);
        entity.setPriority(1);
        entity.setPrice(100.0);
        entity.setCurrency("EUR");

        // When
        Price price = priceMapper.mapFromEntityToPrices(entity);

        // Then
        assertEquals(entity.getBrandId(), price.getBrandId().getValue());
        assertEquals(entity.getStartDate(), price.getStartDate());
        assertEquals(entity.getEndDate(), price.getEndDate());
        assertEquals(entity.getPriceListId(), price.getPriceList());
        assertEquals(entity.getPriority(), price.getPriority());
        assertEquals(entity.getPrice(), price.getPriceValue());
        assertEquals(entity.getProductId(), price.getProductId());
        assertEquals(entity.getCurrency(), price.getCurrency().getCurrencyCode());
    }
}