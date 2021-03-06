package dev.oldboy.navita.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import dev.oldboy.navita.gateway.exceptions.AuthorizationException;
import dev.oldboy.navita.gateway.utils.JwtTokenUtils;
import io.jsonwebtoken.MalformedJwtException;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationGlobalFilter implements GlobalFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
    
    String requestId = exchange.getLogPrefix().replace("[", "").replace("]", "").trim();
    
    ServerHttpRequest request = exchange.getRequest().mutate().header("RequestId", requestId).build();
    exchange.mutate().request(request).build();

    System.out.println(route.getId());
    System.out.println(exchange.getLogPrefix());
    System.out.println(exchange.getAttributes());
    System.out.println(exchange.getResponse());
    System.out.println(exchange.getRequest().getId());
    System.out.println(exchange.getRequest().getHeaders());
    System.out.println(exchange.getRequest().getRemoteAddress());
    System.out.println(exchange.getRequest().getMethodValue());
    
    if(route.getId().contains("auth")) {
      return chain.filter(exchange);
    }else {
      String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
      
      if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        String token = authorizationHeader.substring(7);
        
        if(token.isBlank() || token.equals("undefined")) {
          throw new MalformedJwtException("JWT Token is invalid");
        }
        
        JwtTokenUtils jwtTokenUtils = new JwtTokenUtils();
        Boolean validToken = jwtTokenUtils.validated(token);
        
        if(validToken) {  
          return chain.filter(exchange);
        }
      }
      
      throw new AuthorizationException("Request does not contain Header Authorization with token"); 
    }    
  }
  
}
