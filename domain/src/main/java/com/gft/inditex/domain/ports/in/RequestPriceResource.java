package com.gft.inditex.domain.ports.in;

import com.gft.inditex.domain.RequestData;
import com.gft.inditex.domain.ResponseData;
import java.util.concurrent.CompletableFuture;


public interface RequestPriceResource {

    CompletableFuture<ResponseData> requestPrice(RequestData requestData);
}
