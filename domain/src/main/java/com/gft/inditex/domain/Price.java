package com.gft.inditex.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Currency;

@AllArgsConstructor
@Getter
@Builder
public class Price {

    private Brands brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Integer priority;
    private Double priceValue;
    private Integer productId;
    private Currency currency;

}
