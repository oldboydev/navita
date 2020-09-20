package dev.oldboy.navita.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

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
}