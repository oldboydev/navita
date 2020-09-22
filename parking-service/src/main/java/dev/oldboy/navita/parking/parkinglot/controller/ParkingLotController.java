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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.oldboy.navita.parking.parkinglot.dto.RequestParkingLotDto;
import dev.oldboy.navita.parking.parkinglot.dto.ResponseParkingLotDto;
import dev.oldboy.navita.parking.parkinglot.service.ParkingLotService;
import dev.oldboy.navita.parking.shared.models.ErrorDetail;

@RestController
@RequestMapping(value = "/parking-lot", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
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
  
}
