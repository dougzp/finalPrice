package com.gft.inditex.persistence.infra.adapters.out;

import com.gft.inditex.domain.Brands;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface SpringPricesRepository extends ReactiveCrudRepository<PriceEntity, UUID> {

    @Query("SELECT * FROM prices  WHERE brand_id = :brandId " +
            "AND start_date <= :applicationDate " +
            "AND end_date >= :applicationDate " +
            "AND product_id = :productId " +
            "ORDER BY priority DESC LIMIT 1")
    Mono<PriceEntity> findByBrandIdAndApplicationDateAndProductId(
            @Param("brandId") Integer brandId,
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("productId") Integer productId);


}
