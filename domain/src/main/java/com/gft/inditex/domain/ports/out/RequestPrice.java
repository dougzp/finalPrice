package com.gft.inditex.domain.ports.out;


import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.Price;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface RequestPrice {

    Mono<Price> execute(LocalDateTime applicationDate, Integer productId, Brands brand) ;
}
