package com.gft.inditex.domain;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Currency;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceTest {

    @Test
    void should_create_a_new_price_with_correct_values(){

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusHours(1);
        Currency euros = Currency.getInstance("EUR");
        Price price = Price.builder().currency(euros)
                .brandId(Brands.ZARA)
                .priceList(1)
                .startDate(startDate)
                .endDate(endDate)
                .priority(1)
                .priceValue(30.50)
                .build();
        assertEquals(euros, price.getCurrency());
        assertEquals(Brands.ZARA, price.getBrandId());
        assertEquals(1, price.getPriceList());
        assertEquals(startDate, price.getStartDate());
        assertEquals(endDate, price.getEndDate());
        assertEquals(1, price.getPriority());
        assertEquals(30.50, price.getPriceValue());


    }

}