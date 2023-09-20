package com.gft.inditex.persistence.infra.adapters.out;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prices")  // This is from Spring Data R2DBC
public class PriceEntity {

    @Id  // This is from Spring Data R2DBC
    private UUID id;

    @Column("brand_id")
    private Integer brandId;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

    @Column("price_list_id")
    private Integer priceListId;

    @Column("priority")
    private Integer priority;

    @Column("product_id")
    private Integer productId;

    @Column("price")
    private Double price;

    @Column("currency")
    private String currency;
}
