package com.gft.inditex.persistence.infra.adapters.out;

import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.Price;
import com.gft.inditex.domain.exception.NotFoundException;
import com.gft.inditex.domain.ports.out.PricePersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class PricePersistenceImpl implements PricePersistence {

    private final SpringPricesRepository repository;
    private final PriceMapper priceMapper = new PriceMapper();

    @Override
    public CompletableFuture<Price> getPricesForZaraBrand(LocalDateTime applicationDate, Integer productId, Brands brand) {
        return repository
                .findByBrandIdAndApplicationDateAndProductId(brand.getValue(), applicationDate, productId)
                .switchIfEmpty(Mono.error(new NotFoundException("Entity not found for brand: " + brand + ", applicationDate: " + applicationDate + ", productId: " + productId)))
                .map(priceMapper::mapFromEntityToPrices)
                .toFuture();  // Convert the Mono<Price> to CompletableFuture<Price>
    }
}