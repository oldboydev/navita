package dev.oldboy.navita.gateway.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Order(-1)
@Configuration
public class GlobalExceptionHandler implements ErrorWebExceptionHandler{
    
  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    ServerHttpResponse response = exchange.getResponse();
 
    //header set
    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    if (ex instanceof ResponseStatusException) {
      response.setStatusCode(((ResponseStatusException) ex).getStatus());
    }else {
      response.setStatusCode(HttpStatus.BAD_REQUEST);
    }
    
    return response.writeWith(Mono.fromSupplier(() -> {
      DataBufferFactory bufferFactory = response.bufferFactory();
      String msg = ex.getMessage();
      
      return bufferFactory.wrap(msg.getBytes());
    }));
  }
  
}
