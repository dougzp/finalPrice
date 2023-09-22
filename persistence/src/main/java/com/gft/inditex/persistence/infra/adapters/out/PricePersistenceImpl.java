package com.gft.inditex.persistence.infra.adapters.out;

import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.Price;
import com.gft.inditex.domain.ports.out.PricePersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PricePersistenceImpl implements PricePersistence {

    private final SpringPricesRepository repository;
    private final PriceMapper priceMapper = new PriceMapper();
    @Override
    public Mono<Price> getPricesForZaraBrand(LocalDateTime applicationDate, Integer productId, Brands brand) {
        Mono<PriceEntity> repoResult = repository.findByBrandIdAndApplicationDateAndProductId(brand.getValue(), applicationDate, productId);
        return repoResult.map(priceMapper::mapFromEntityToPrices);
    }
}
