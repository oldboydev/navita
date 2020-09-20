package dev.oldboy.navita.auth.security.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.oldboy.navita.auth.security.dtos.RequestAuthDto;
import dev.oldboy.navita.auth.shared.dtos.ResponseDto;
import dev.oldboy.navita.auth.shared.models.ErrorDetail;

@RestController
@RequestMapping("/auth")
public class AuthController {
  
  @Autowired
  private AuthenticationManager authenticationManager;
  
  @Autowired
  private UserDetailsService userDetailsService;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @PostMapping("/authenticate")
  public ResponseEntity<ResponseDto> authenticate(@RequestBody RequestAuthDto request){
    ResponseDto responseDto = new ResponseDto();
    
    try {
      authenticationManager.authenticate(
         new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
      );
    }catch (BadCredentialsException e) {
      ErrorDetail error = new ErrorDetail("Bad Credentials", "Incorrect Username or password");
      List<ErrorDetail> errors = new ArrayList<>();
      errors.add(error);
      
      responseDto.setStatus(403);
      responseDto.setMessage("See error details for more info");
      responseDto.setErrors(errors);
      System.out.println(passwordEncoder.encode(request.getPassword()));
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
    }
    
    
    
    return ResponseEntity.ok(responseDto);
  }
  
}
