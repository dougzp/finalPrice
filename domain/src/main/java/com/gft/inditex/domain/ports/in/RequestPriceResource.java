package com.gft.inditex.domain.ports.in;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface RequestPriceResource {

    Mono<ServerResponse> requestPrice(ServerRequest request);
}
