package com.gft.inditex.finalprice.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gft.inditex.domain.RequestData;
import com.gft.inditex.domain.ResponseData;
import com.gft.inditex.finalprice.infra.adapters.in.PriceDTO;
import com.gft.inditex.finalprice.infra.adapters.in.exceptions.SerializationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Component
public class ConversionHandler {
    private final ObjectMapper objectMapper;

    public ConversionHandler() {
        this.objectMapper =  new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")); // custom date format
        this.objectMapper.setTimeZone(TimeZone.getTimeZone("UTC")); // set time zone
    }

    public RequestData fromServerRequest(ServerRequest serverRequest) {
        Map<String, String> pathVars = new HashMap<>(serverRequest.pathVariables());
        Map<String, String> queryParams = serverRequest.queryParams().toSingleValueMap();

        return new RequestData(pathVars, queryParams, null); // Assuming you don't need the request body for now
    }


    public ResponseData fromPriceDTO(PriceDTO priceDTO) {
        try {
            String jsonBody = this.objectMapper.writeValueAsString(priceDTO);
            return new ResponseData(null, null, jsonBody);
        } catch (Exception e) {
            throw new SerializationException("Error serializing PriceDTO to JSON", e);
        }
    }

    public static Mono<ServerResponse> toServerResponse(ResponseData responseData) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(responseData.getBody()), String.class);
    }
}