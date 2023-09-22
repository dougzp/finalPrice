package com.gft.inditex.domain.ports.out;

import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.Price;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public interface PricePersistence {
        CompletableFuture<Price> getPricesForZaraBrand(LocalDateTime applicationDate, Integer productId, Brands brand);
    }


