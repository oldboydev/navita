package dev.oldboy.navita.parking.parkinglot.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.oldboy.navita.parking.parkinglot.dto.RequestParkingLotDto;
import dev.oldboy.navita.parking.parkinglot.dto.ResponseDeleteParkingLotDto;
import dev.oldboy.navita.parking.parkinglot.dto.ResponseParkingLotDto;
import dev.oldboy.navita.parking.parkinglot.exceptions.ParkingLotNotFoundException;
import dev.oldboy.navita.parking.parkinglot.service.ParkingLotService;
import dev.oldboy.navita.parking.shared.models.ErrorDetail;

@RestController
@RequestMapping(value = "/parking/parking-lot", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class ParkingLotController {
  
  @Autowired
  private ParkingLotService service;
  
  @PostMapping()
  public ResponseEntity<ResponseParkingLotDto> addParkingLot(@RequestBody @Validated RequestParkingLotDto requestBody){
    ResponseParkingLotDto responseDto = new ResponseParkingLotDto();
    
    try {
      responseDto.setData(Arrays.asList(service.addParkingLot(requestBody)));
      responseDto.setStatus(201);
      responseDto.setMessage("Success: parking lot created");
      
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    } catch (Exception e) {
      ErrorDetail error;
      
      if(e.getCause() instanceof ConstraintViolationException) {
        if(e.getMessage().contains("uk_cnpj")){
          error = new ErrorDetail("Unique constraint", "CNPJ: " + requestBody.getCnpj() + " is already record for another user!");
        }else {
          error = new ErrorDetail("Unique constraint", e.getMessage());
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
  
  @GetMapping()
  public ResponseEntity<ResponseParkingLotDto> findAllParkingLot(){
    ResponseParkingLotDto responseDto = new ResponseParkingLotDto();
    
    try {
      responseDto.setData(service.findAll());
      responseDto.setStatus(200);
      responseDto.setMessage("Success: found #" + responseDto.getData().size() + " parking lots.");
      
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
  public ResponseEntity<ResponseParkingLotDto> findParkingLotById(@PathVariable Long id){
    ResponseParkingLotDto responseDto = new ResponseParkingLotDto();
    
    try {
      responseDto.setData(Arrays.asList(service.findById(id)));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: found #" + responseDto.getData().size() + " parking lot.");
      
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
  
  @GetMapping("/cnpj/{cnpj}")
  public ResponseEntity<ResponseParkingLotDto> findParkingLotByCnpj(@PathVariable String cnpj){
    ResponseParkingLotDto responseDto = new ResponseParkingLotDto();
    
    try {
      responseDto.setData(Arrays.asList(service.findByCnpf(cnpj)));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: found #" + responseDto.getData().size() + " parking lot.");
      
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
  
  @PutMapping("/{id}")
  public ResponseEntity<ResponseParkingLotDto> updateParkingLot(@PathVariable Long id, @RequestBody RequestParkingLotDto requestBody){
    ResponseParkingLotDto responseDto = new ResponseParkingLotDto();
    
    try {
      responseDto.setData(Arrays.asList(service.updatePakingLot(requestBody, id)));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: update #" + responseDto.getData().size() + " parking lot.");
      
      return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    } catch (Exception e) {
      ErrorDetail error;
      
      if(e.getCause() instanceof ParkingLotNotFoundException) {
        error = new ErrorDetail("Record not found", e.getMessage());
      }else {
        error = new ErrorDetail(e.getClass().getName(), e.getMessage());
      }
      
      List<ErrorDetail> errors = new ArrayList<>();
      errors.add(error);
      
      responseDto.setStatus(400);
      responseDto.setMessage("Error: See error details for more info");
      responseDto.setErrors(errors);
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDeleteParkingLotDto> deleteParkingLot(@PathVariable Long id){
    ResponseDeleteParkingLotDto responseDto = new ResponseDeleteParkingLotDto();
    
    try {
      responseDto.setData(service.deleteById(id));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: delete parking lot.");
      
      return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    } catch (Exception e) {
      ErrorDetail error;
      
      if(e.getCause() instanceof ParkingLotNotFoundException) {
        error = new ErrorDetail("Record not found", e.getMessage());
      }else {
        error = new ErrorDetail(e.getClass().getName(), e.getMessage());
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
