package dev.oldboy.navita.gateway.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationServiceRoutes {
  
  @Bean
  public RouteLocator authenticationRoutes(RouteLocatorBuilder builder) {
     return builder.routes()
         .route(r -> r.path("/auth/authenticate")
             .uri("lb://navita-auth/auth/authenticate")
             .id("auth")
         ).route(r -> r.path("/users/**")
             .uri("lb://navita-auth/users/**")
             .id("users")
         ).route(r -> r.path("/parking/**")
             .uri("lb://navita-parking/parking/**")
             .id("parking")
         ).build();  
  }     
}
