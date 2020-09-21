package dev.oldboy.navita.gateway.exceptions;

import java.util.ArrayList;
import java.util.List;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.oldboy.navita.gateway.dtos.ResponseDto;
import dev.oldboy.navita.gateway.models.ErrorDetail;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import reactor.core.publisher.Mono;

@Order(-1)
@Configuration
public class GlobalExceptionHandler implements ErrorWebExceptionHandler{
    
  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    ServerHttpResponse response = exchange.getResponse();
    //header set
    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    
    //build response body by error type
    String responseBody;
    if(ex instanceof ResponseStatusException) {
      response.setStatusCode(((ResponseStatusException) ex).getStatus());
      responseBody = ex.getMessage();
    }else if(ex instanceof MalformedJwtException) {
      response.setStatusCode(HttpStatus.BAD_REQUEST);
      responseBody = buildResponse("Malformed JWT", "JWT Token is invalid", 400);
    }else if(ex instanceof ExpiredJwtException) {
      response.setStatusCode(HttpStatus.BAD_REQUEST);
      responseBody = buildResponse("Expire JWT", "JWT Token is expired", 400);
    }else if(ex instanceof AuthorizationException) {
      response.setStatusCode(HttpStatus.FORBIDDEN);
      responseBody = buildResponse("Anauthorized Request", ex.getMessage(), 403);
    }else {
      responseBody = buildResponse("Unknow Error", ex.getMessage(), 500);
    }
    
    return response.writeWith(Mono.fromSupplier(() -> {
      DataBufferFactory bufferFactory = response.bufferFactory();
      
      return bufferFactory.wrap(responseBody.getBytes());
    }));
  }
  
  private String buildResponse(String type, String msg, Integer status) {
    try {
      ResponseDto responseDto = new ResponseDto();
      
      List<ErrorDetail> errors = new ArrayList<>();
      ErrorDetail error = new ErrorDetail(type, msg);
      errors.add(error);
      
      responseDto.setStatus(status);
      responseDto.setMessage("Error: See error details for more info");
      responseDto.setErrors(errors);
      
      ObjectMapper mapper = new ObjectMapper();
      
      return mapper.writeValueAsString(responseDto);
    }catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    
    return null;
  }
}
