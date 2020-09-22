package dev.oldboy.navita.parking.manager.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.oldboy.navita.parking.manager.dtos.RequestManagerDto;
import dev.oldboy.navita.parking.manager.services.ManagerService;
import dev.oldboy.navita.parking.shared.models.ErrorDetail;
import dev.oldboy.navita.parking.vehicle.dtos.ResponseConfirmDto;

@RestController
@RequestMapping(value = "/parking/manager", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class ManageController {
  
  @Autowired
  private ManagerService service;
  
  @PostMapping("/park")
  public ResponseEntity<ResponseConfirmDto> parkVehicle(@RequestBody @Validated RequestManagerDto requestBody){
    ResponseConfirmDto responseDto = new ResponseConfirmDto();
    
    try {
      responseDto.setData(service.parkingVehicle(requestBody));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: parking / unpaking vehicle");
      
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    } catch (Exception e) {
      ErrorDetail error = new ErrorDetail("Illegal operation", e.getMessage());
      List<ErrorDetail> errors = new ArrayList<>();
      errors.add(error);
      
      responseDto.setStatus(400);
      responseDto.setMessage("Error: See error details for more info");
      responseDto.setErrors(errors);
      
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
  }
  
}
