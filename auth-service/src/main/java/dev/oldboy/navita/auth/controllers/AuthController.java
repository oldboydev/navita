package dev.oldboy.navita.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.oldboy.navita.auth.models.User;
import dev.oldboy.navita.auth.repositories.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
  
  @Autowired
  private UserRepository repository;
  
  @PostMapping("/authenticate")
  public void authenticate(){
    
  }
  
}
