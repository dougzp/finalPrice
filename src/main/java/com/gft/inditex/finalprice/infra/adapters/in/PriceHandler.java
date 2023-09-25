package com.gft.inditex.finalprice.infra.adapters.in;

import com.gft.inditex.domain.Brands;
import com.gft.inditex.domain.RequestData;
import com.gft.inditex.domain.ResponseData;
import com.gft.inditex.domain.exception.NotFoundException;
import com.gft.inditex.domain.ports.in.RequestPriceResource;
import com.gft.inditex.domain.ports.out.RequestPrice;
import com.gft.inditex.finalprice.application.ConversionHandler;
import com.gft.inditex.finalprice.infra.adapters.in.exceptions.InvalidBrandException;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class PriceHandler implements RequestPriceResource {

    public static final String BRAND = "brand";
    public static final String PRODUCT_ID = "product_id";
    public static final String APPLICATION_DATE = "application_date";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd-HH.mm.ss";
    public static final String INVALID_DATE_FORMAT_MESSAGE = "Invalid date format. Please use 'yyyy-MM-dd-HH.mm.ss'.";
    public static final String INVALID_PRODUCT_ID_FORMAT_MESSAGE = "Invalid product ID format. Please provide a valid number.";
    public static final String INVALID_BRAND_NAME_MESSAGE = "Invalid brand name provided.";
    private final RequestPrice requestPrice;
    private final ConversionHandler conversionHandler;

    public Mono<ServerResponse> handleRequestPrice(ServerRequest serverRequest) {
        RequestData requestData = this.conversionHandler.fromServerRequest(serverRequest);

        return Mono.defer(() -> Mono.fromFuture(() -> requestPrice(requestData)))
                .flatMap(ConversionHandler::toServerResponse)
                .onErrorResume(this::generateErrorResponse);
    }

    private Mono<ServerResponse> generateErrorResponse(Throwable ex) {
        AtomicReference<Throwable> actualExceptionRef = new AtomicReference<>(ex);

        if (ex instanceof CompletionException) {
            actualExceptionRef.set(ex.getCause());
        }

        Map<Class<? extends Throwable>, HttpStatus> errorMapping = new HashMap<>();
        errorMapping.put(NotFoundException.class, HttpStatus.NOT_FOUND);
        errorMapping.put(InvalidDateFormatException.class, HttpStatus.BAD_REQUEST);
        errorMapping.put(InvalidProductIdException.class, HttpStatus.BAD_REQUEST);
        errorMapping.put(InvalidBrandException.class, HttpStatus.BAD_REQUEST);

        return Optional.ofNullable(errorMapping.get(actualExceptionRef.get().getClass()))
                .map(status -> ServerResponse.status(status).body(BodyInserters.fromValue(actualExceptionRef.get().getMessage())))
                .orElseGet(() -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BodyInserters.fromValue(actualExceptionRef.get().getMessage())));
    }
    @Override
    public CompletableFuture<ResponseData> requestPrice(RequestData requestData) {
        try {
            LocalDateTime applicationDate = parseApplicationDate(requestData);
            int productId = parseProductId(requestData);
            Brands brand = parseBrand(requestData);

            return this.requestPrice.execute(applicationDate, productId, brand)
                    .thenApply(price -> {
                        PriceDTO priceDTO = PriceDTO.mapDomainToDTO(price);
                        return this.conversionHandler.fromPriceDTO(priceDTO);
                    });
        } catch (InvalidDateFormatException | InvalidProductIdException | InvalidBrandException e) {
            CompletableFuture<ResponseData> errorFuture = new CompletableFuture<>();
            errorFuture.completeExceptionally(e);
            return errorFuture;
        }
    }

    private LocalDateTime parseApplicationDate(RequestData requestData) throws InvalidDateFormatException {
        try {
            return LocalDateTime.parse(requestData.getPathVariables().get(APPLICATION_DATE), DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(INVALID_DATE_FORMAT_MESSAGE);
        }
    }

    private int parseProductId(RequestData requestData) throws InvalidProductIdException {
        try {
            return Integer.parseInt(requestData.getPathVariables().get(PRODUCT_ID));
        } catch (NumberFormatException e) {
            throw new InvalidProductIdException(INVALID_PRODUCT_ID_FORMAT_MESSAGE);
        }
    }
    private Brands parseBrand(RequestData requestData) throws InvalidBrandException {
        try {
            return Brands.valueOf(requestData.getPathVariables().get(BRAND).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidBrandException(INVALID_BRAND_NAME_MESSAGE);
        }
    }
}