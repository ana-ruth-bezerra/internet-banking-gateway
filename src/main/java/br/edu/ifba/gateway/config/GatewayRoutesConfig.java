package br.edu.ifba.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            // Rota específica para login direto (sem prefixo)
            .route("login-direct", r -> r
                .path("/login")
                .uri("lb://internetBanking"))
            
            // Rota específica para auth/login direto (sem prefixo)
            .route("auth-login-direct", r -> r
                .path("/auth/login")
                .uri("lb://internetBanking"))
            
            // Rota para o serviço de internet banking com reescrita de caminho
            .route("internet-banking", r -> r
                .path("/internetbanking/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://internetBanking"))
            
            // Rota para o serviço de email
            .route("email-service", r -> r
                .path("/email/**")
                .uri("lb://email-service"))
            
            // Rota para outras operações do internet banking
            .route("banking-operations", r -> r
                .path("/users/**", "/operations/**", "/accounts/**")
                .uri("lb://internetBanking"))
            
            .build();
    }
}
