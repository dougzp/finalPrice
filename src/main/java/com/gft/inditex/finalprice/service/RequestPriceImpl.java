package com.gft.inditex.finalprice.service;

import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.Price;
import com.gft.inditex.finalprice.application.ports.out.RequestPrice;
import com.gft.inditex.persistence.infra.ports.out.PricePersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;



@Service
@RequiredArgsConstructor
public class RequestPriceImpl implements RequestPrice {


    private final PricePersistence repository;

    @Override
    public Mono<Price> execute(LocalDateTime applicationDate, Integer productId, Brands brand) {
        return repository.getPricesForZaraBrand(applicationDate,productId,brand);
    }
}
