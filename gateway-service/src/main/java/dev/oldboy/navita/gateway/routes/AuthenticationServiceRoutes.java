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
         .route(r -> r.path("/roles")
             .uri("lb://bp-user-management-service/roles")
             .id("auth")
         ).build();  
  }   
  
}
