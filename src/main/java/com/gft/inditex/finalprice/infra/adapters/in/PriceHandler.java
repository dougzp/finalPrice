package com.gft.inditex.finalprice.infra.adapters.in;

import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.RequestData;
import com.gft.inditex.domain.ResponseData;
import com.gft.inditex.domain.ports.in.RequestPriceResource;
import com.gft.inditex.domain.ports.out.RequestPrice;
import com.gft.inditex.finalprice.application.ConversionHandler;
import com.gft.inditex.finalprice.infra.adapters.in.exceptions.InvalidDateFormatException;
import com.gft.inditex.finalprice.infra.adapters.in.exceptions.InvalidProductIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
@RequiredArgsConstructor
public class PriceHandler implements RequestPriceResource {

    private final RequestPrice requestPrice;
    private final ConversionHandler conversionHandler;
    public Mono<ServerResponse> handleRequestPrice(ServerRequest serverRequest) {
        RequestData requestData = this.conversionHandler.fromServerRequest(serverRequest);
        return Mono.fromFuture(() -> requestPrice(requestData))
                .flatMap(ConversionHandler::toServerResponse)  // Adjusted this line
                .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BodyInserters.fromValue(ex.getMessage())));
    }
    @Override
    public CompletableFuture<ResponseData> requestPrice(RequestData requestData) {
        try {
            LocalDateTime applicationDate = parseApplicationDate(requestData);
            int productId = parseProductId(requestData);
            Brands brand = Brands.valueOf(requestData.getPathVariables().get("brand").toUpperCase());

            return this.requestPrice.execute(applicationDate, productId, brand)
                    .thenApply(price -> {
                        PriceDTO priceDTO = PriceDTO.mapDomainToDTO(price);
                        return this.conversionHandler.fromPriceDTO(priceDTO);
                    })
                    .exceptionally(ex -> {
                        throw new CompletionException(ex);
                    });

        } catch (InvalidDateFormatException | InvalidProductIdException e) {
            CompletableFuture<ResponseData> errorFuture = new CompletableFuture<>();
            errorFuture.completeExceptionally(e);
            return errorFuture;
        }
    }

    private LocalDateTime parseApplicationDate(RequestData requestData) throws InvalidDateFormatException {
        try {
            return LocalDateTime.parse(requestData.getPathVariables().get("application_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss"));
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid date format. Please use 'yyyy-MM-dd-HH.mm.ss'.");
        }
    }

    private int parseProductId(RequestData requestData) throws InvalidProductIdException {
        try {
            return Integer.parseInt(requestData.getPathVariables().get("product_id"));
        } catch (NumberFormatException e) {
            throw new InvalidProductIdException("Invalid product ID format. Please provide a valid number.");
        }
    }
}