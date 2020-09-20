package dev.oldboy.navita.auth.security.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtils {
  
  private final String SECRET_KEY = "navitatestebackendpaulofmonteiro";
  //2h
  private final Integer JWT_EXPIRATION_MULTIPLIER = 1000 * 60 * 60 * 2;
  
  public Integer getExpirationTime() {
    return JWT_EXPIRATION_MULTIPLIER;
  }
  
  public Boolean validated(String token) {
    System.out.println(token);
    
    Claims jws = Jwts.parser() 
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token).getBody();
    
   return true;
  }
  
  public String generateToken(UserDetails userDetails) {
    //because don't use roles in this implementation this will be empty
    Map<String, Object> claims = new HashMap<>();
    
    return createToken(claims, userDetails.getUsername());
  }
  
  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MULTIPLIER))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
  }
}