package com.gft.inditex.persistence.infra.ports.out;

import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.Price;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PricePersistence {

    Mono<Price> getPricesForZaraBrand(LocalDateTime applicationDate, Integer productId, Brands brand) ;

}
