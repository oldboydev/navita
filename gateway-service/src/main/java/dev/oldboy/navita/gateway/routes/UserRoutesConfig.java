package dev.oldboy.navita.gateway.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRoutesConfig {
  
  @Bean
  public RouteLocator userRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(r -> r.path("/users")
          .uri("lb://bp-user-management-service/users")
          
         )        
    .build();
  }
  
}
