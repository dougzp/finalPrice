package com.gft.inditex.finalprice.infra.adapters.in;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PriceRouter {

    private static final String PATH = "getPrice";

    @Bean
    RouterFunction<ServerResponse> router(PriceHandler handler){
        return RouterFunctions.route().GET(PATH +"/{application_date}/{product_id}/{brand}", handler::requestPrice).build();

    }

}
