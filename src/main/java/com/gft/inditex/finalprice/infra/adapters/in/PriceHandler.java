package com.gft.inditex.finalprice.infra.adapters.in;

import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.ports.in.RequestPriceResource;
import com.gft.inditex.domain.ports.out.RequestPrice;
import com.gft.inditex.finalprice.infra.adapters.in.exceptions.InvalidDateFormatException;
import com.gft.inditex.finalprice.infra.adapters.in.exceptions.InvalidProductIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
@RequiredArgsConstructor
public class PriceHandler implements RequestPriceResource {


    private final RequestPrice requestPrice;

    @Override
    public Mono<ServerResponse> requestPrice(ServerRequest request) {
        try {
            LocalDateTime applicationDate = parseApplicationDate(request);
            int productId = parseProductId(request);
            Brands brand = Brands.valueOf(request.pathVariable("brand").toUpperCase());
            return this.requestPrice.execute(applicationDate, productId, brand)
                    .map(PriceDTO::mapDomainToDTO) // Transform Price into PriceDTO
                    .flatMap(priceDTO -> ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(priceDTO)))
                    .switchIfEmpty(ServerResponse.notFound().build());

        } catch (InvalidDateFormatException | InvalidProductIdException e) {
            return Mono.error(e);
        }
    }

    private LocalDateTime parseApplicationDate(ServerRequest request) throws InvalidDateFormatException {
        try {
            return LocalDateTime.parse(request.pathVariable("application_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss"));
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid date format. Please use 'yyyy-MM-dd-HH.mm.ss'.");
        }
    }

    private int parseProductId(ServerRequest request) throws InvalidProductIdException {
        try {
            return Integer.parseInt(request.pathVariable("product_id"));
        } catch (NumberFormatException e) {
            throw new InvalidProductIdException("Invalid product ID format. Please provide a valid number.");
        }
    }
}