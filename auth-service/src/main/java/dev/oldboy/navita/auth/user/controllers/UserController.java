package dev.oldboy.navita.auth.user.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.oldboy.navita.auth.shared.models.ErrorDetail;
import dev.oldboy.navita.auth.user.dto.RequestUserDto;
import dev.oldboy.navita.auth.user.dto.ResponseCreateUserDto;
import dev.oldboy.navita.auth.user.dto.ResponseDeleteDto;
import dev.oldboy.navita.auth.user.dto.ResponseUserDto;
import dev.oldboy.navita.auth.user.services.UserService;

@RestController
@RequestMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserController {
  
  @Autowired
  private UserService userService;
  
  @PostMapping
  public ResponseEntity<ResponseCreateUserDto> addUser(@RequestBody @Validated RequestUserDto requestBody){
    ResponseCreateUserDto responseDto = new ResponseCreateUserDto();
    
    try {
      responseDto.setData(userService.addUser(requestBody));
      responseDto.setStatus(201);
      responseDto.setMessage("Success: user created");
      
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    } catch (Exception e) {
      ErrorDetail error;
  
      if(e.getCause() instanceof ConstraintViolationException) {
        if(e.getMessage().contains("uk_cpf")){
          error = new ErrorDetail("Unique constraint", "CPF: " + requestBody.getCpf() + " is already record for another user!");
        }else {
          error = new ErrorDetail("Unique constraint", "Email: " + requestBody.getEmail() + " is already record for another user!");
        }        
      }else {
        error = new ErrorDetail("Incorrect Request", e.getMessage());
      }
      
      List<ErrorDetail> errors = new ArrayList<>();
      errors.add(error);
      
      responseDto.setStatus(400);
      responseDto.setMessage("Error: See error details for more info");
      responseDto.setErrors(errors);
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
  }
  
  @GetMapping
  public ResponseEntity<ResponseUserDto> getAllUsers(){
    ResponseUserDto responseDto = new ResponseUserDto();
    
    try {
      responseDto.setData(userService.findAll());
      responseDto.setStatus(200);
      responseDto.setMessage("Success: found #" + responseDto.getData().size() + " users.");
      
      return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    } catch (Exception e) {
      ErrorDetail error = new ErrorDetail("Record not found", e.getMessage());
      List<ErrorDetail> errors = new ArrayList<>();
      errors.add(error);
      
      responseDto.setStatus(400);
      responseDto.setMessage("Error: See error details for more info");
      responseDto.setErrors(errors);
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<ResponseUserDto> getUserById(@PathVariable Long id){
    ResponseUserDto responseDto = new ResponseUserDto();
    
    try {
      responseDto.setData(Arrays.asList(userService.findUserById(id)));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: found #" + responseDto.getData().size() + " users.");
      
      return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    } catch (Exception e) {
      ErrorDetail error = new ErrorDetail("Record not found", e.getMessage());
      List<ErrorDetail> errors = new ArrayList<>();
      errors.add(error);
      
      responseDto.setStatus(400);
      responseDto.setMessage("Error: See error details for more info");
      responseDto.setErrors(errors);
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
  }
  
  @GetMapping("/user/{email}")
  public ResponseEntity<ResponseUserDto> getUserByEmail(@PathVariable String email){
    ResponseUserDto responseDto = new ResponseUserDto();
    
    try {
      responseDto.setData(Arrays.asList(userService.findUserByEmail(email)));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: found #" + responseDto.getData().size() + " users.");
      
      return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    } catch (Exception e) {
      ErrorDetail error = new ErrorDetail("Record not found", e.getMessage());
      List<ErrorDetail> errors = new ArrayList<>();
      errors.add(error);
      
      responseDto.setStatus(400);
      responseDto.setMessage("Error: See error details for more info");
      responseDto.setErrors(errors);
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDeleteDto> deleteUserById(@PathVariable Long id){
    ResponseDeleteDto responseDto = new ResponseDeleteDto();
    
    try {
      responseDto.setData(userService.deleteUserById(id));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: deleted user with id: " + id);
      
      return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    } catch (Exception e) {
      ErrorDetail error;

      if(e.getClass() == IllegalArgumentException.class) {
        error = new ErrorDetail("Illegal operation", e.getMessage());
      }else {
        error = new ErrorDetail("Record not found", e.getMessage());
      }
      
      List<ErrorDetail> errors = new ArrayList<>();
      errors.add(error);
      
      responseDto.setStatus(400);
      responseDto.setMessage("Error: See error details for more info");
      responseDto.setErrors(errors);
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
  }
  
  @DeleteMapping("/user/{email}")
  public ResponseEntity<ResponseDeleteDto> deleteUserByEmail(@PathVariable String email){
    ResponseDeleteDto responseDto = new ResponseDeleteDto();
    
    try {
      responseDto.setData(userService.deleteUserByEmail(email));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: deleted user with email: " + email);
      
      return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    } catch (Exception e) {
      ErrorDetail error;
      
      if(e.getClass() == IllegalArgumentException.class) {
        error = new ErrorDetail("Illegal operation", e.getMessage());
      }else {
        error = new ErrorDetail("Record not found", e.getMessage());
      }
      
      List<ErrorDetail> errors = new ArrayList<>();
      errors.add(error);
      
      responseDto.setStatus(400);
      responseDto.setMessage("Error: See error details for more info");
      responseDto.setErrors(errors);
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
  }
}
