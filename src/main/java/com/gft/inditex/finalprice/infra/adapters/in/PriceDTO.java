package com.gft.inditex.finalprice.infra.adapters.in;

import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.Price;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Currency;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceDTO {

    private Integer productId;
    private Brands brandId;
    private Integer priceList;
    private Double priceValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Currency currency;




    public static PriceDTO mapDomainToDTO(Price price){
       return PriceDTO.builder()
                .brandId(price.getBrandId())
                .productId(price.getProductId())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .priceList(price.getPriceList())
                .priceValue(price.getPriceValue())
                .currency(price.getCurrency()).build();
    }

}
