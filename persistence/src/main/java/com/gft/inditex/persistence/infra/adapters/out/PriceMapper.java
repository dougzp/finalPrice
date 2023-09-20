package com.gft.inditex.persistence.infra.adapters.out;

import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.Price;

import java.util.Currency;

public class PriceMapper {

    public Price mapFromEntityToPrices(PriceEntity entity) {

        return Price.builder()
                .brandId(Brands.getByValue(entity.getBrandId()))
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priceList(entity.getPriceListId()) // Assuming this maps to priceList in Prices
                .priority(entity.getPriority())
                .priceValue(entity.getPrice())
                .productId(entity.getProductId())
                .currency(Currency.getInstance(entity.getCurrency()))
                .build();

    }
}
