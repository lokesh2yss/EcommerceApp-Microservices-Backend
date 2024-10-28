package com.codingshuttle.ecommerce.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.awt.*;

@Component
@Slf4j
public class LoggingOrderGatewayFilter extends AbstractGatewayFilterFactory<LoggingOrderGatewayFilter.Config> {

    public LoggingOrderGatewayFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Logging from LoggingOrderGatewayFilter:");
            return chain.filter(exchange);
        };
    }
    public static class Config {

    }
}
