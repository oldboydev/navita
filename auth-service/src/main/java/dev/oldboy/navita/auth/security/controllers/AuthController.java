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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.oldboy.navita.auth.security.dtos.RequestAuthDto;
import dev.oldboy.navita.auth.security.services.UserDetailsServiceimpl;
import dev.oldboy.navita.auth.shared.dtos.ResponseDto;
import dev.oldboy.navita.auth.shared.models.ErrorDetail;

@RestController
@RequestMapping("/auth")
public class AuthController {
  
  @Autowired
  private AuthenticationManager authenticationManager;
  
  @Autowired
  private UserDetailsService userDetailsService;
  
  @PostMapping("/authenticate")
  public ResponseEntity<ResponseDto> authenticate(@RequestBody RequestAuthDto request){
    ResponseDto responseDto = new ResponseDto();
    
    try {
      authenticationManager.authenticate(
         new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
      );
      
      UserDetailsServiceimpl userDetailsServiceImpl = (UserDetailsServiceimpl) userDetailsService;
      
      responseDto.setStatus(200);
      responseDto.setMessage("Success: authorization granted");
      responseDto.setData(userDetailsServiceImpl.generateToken());     
      
      return ResponseEntity.ok(responseDto);      
    }catch (BadCredentialsException e) {
      ErrorDetail error = new ErrorDetail("Bad Credentials", "Incorrect Username or password");
      List<ErrorDetail> errors = new ArrayList<>();
      errors.add(error);
      
      responseDto.setStatus(403);
      responseDto.setMessage("Error: See error details for more info");
      responseDto.setErrors(errors);
  
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
    }
  }
  
  @GetMapping("/test")
  public String test() {
    return "autorized";
  }
  
}
