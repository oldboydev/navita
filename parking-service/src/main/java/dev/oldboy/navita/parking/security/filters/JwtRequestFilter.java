package dev.oldboy.navita.parking.security.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.oldboy.navita.parking.security.utils.JwtTokenUtils;
import dev.oldboy.navita.parking.shared.dto.ResponseDto;
import dev.oldboy.navita.parking.shared.models.ErrorDetail;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    
    final String authorizationHeader = request.getHeader("Authorization");
    
    String token;
    JwtTokenUtils jwtTokenUtils = new JwtTokenUtils();
    
    if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      token = authorizationHeader.substring(7);
      
      try {
        Boolean validToken = jwtTokenUtils.validated(token);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
            new UsernamePasswordAuthenticationToken(null, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        
        if(validToken) {
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
          
          filterChain.doFilter(request, response);
        }
      }catch (MalformedJwtException e) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(buildResponse("Malformed JWT", "JWT Token is invalid", 400));
      }catch (ExpiredJwtException e) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(buildResponse("Expire JWT", "JWT Token is expired", 400));
      }catch (Exception e) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().write(buildResponse("Unknow Error", e.getMessage(), 500));
      }
    }else {
      filterChain.doFilter(request, response);
    }    
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
