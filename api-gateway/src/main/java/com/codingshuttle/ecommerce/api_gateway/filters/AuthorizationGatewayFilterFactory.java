package com.codingshuttle.ecommerce.api_gateway.filters;

import com.codingshuttle.ecommerce.api_gateway.auth.JwtService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
public class AuthorizationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizationGatewayFilterFactory.Config> {
    private final JwtService jwtService;
    public AuthorizationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                log.error("Authorization token not found in the request header");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String token = authorizationHeader.split("Bearer ")[1];
            if(token == null || !config.roles.contains(jwtService.getUserRoleFromToken(token))) {
                log.error("The user role doesn't have permission to access this API");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            log.info("The user role is permitted to access this API");
            return chain.filter(exchange);
        };
    }

    @Data
    public static class Config {
        private Set<String> roles;
    }
}
