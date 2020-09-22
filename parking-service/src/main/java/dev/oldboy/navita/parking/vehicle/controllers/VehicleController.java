package dev.oldboy.navita.parking.vehicle.controllers;

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

import dev.oldboy.navita.parking.parkinglot.exceptions.ParkingLotNotFoundException;
import dev.oldboy.navita.parking.shared.models.ErrorDetail;
import dev.oldboy.navita.parking.vehicle.dtos.RequestVehicleDto;
import dev.oldboy.navita.parking.vehicle.dtos.ResponseDeleteVehicleDto;
import dev.oldboy.navita.parking.vehicle.dtos.ResponseVehicleDto;
import dev.oldboy.navita.parking.vehicle.services.VehicleService;

@RestController
@RequestMapping(value = "/parking/vehicles", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class VehicleController {
  
  @Autowired
  private VehicleService service;
  
  @PostMapping
  public ResponseEntity<ResponseVehicleDto> addParkingLot(@RequestBody @Validated RequestVehicleDto requestBody){
    ResponseVehicleDto responseDto = new ResponseVehicleDto();
    
    try {
      responseDto.setData(Arrays.asList(service.addVehicle(requestBody)));
      responseDto.setStatus(201);
      responseDto.setMessage("Success: parking lot created");
      
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    } catch (Exception e) {
      ErrorDetail error;
      
      if(e.getCause() instanceof ConstraintViolationException) {
        if(e.getMessage().contains("uk_plate")){
          error = new ErrorDetail("Unique constraint", "Plate: " + requestBody.getPlate() + " is already record for another user!");
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
  public ResponseEntity<ResponseVehicleDto> findAll(){
    ResponseVehicleDto responseDto = new ResponseVehicleDto();
    
    try {
      responseDto.setData(service.findAll());
      responseDto.setStatus(200);
      responseDto.setMessage("Success: found #" + responseDto.getData().size() + " vehicles.");
      
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
  public ResponseEntity<ResponseVehicleDto> findById(@PathVariable Long id){
    ResponseVehicleDto responseDto = new ResponseVehicleDto();
    
    try {
      responseDto.setData(Arrays.asList(service.findById(id)));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: found #" + responseDto.getData().size() + " vehicle.");
      
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
  
  @GetMapping("/plate/{plate}")
  public ResponseEntity<ResponseVehicleDto> findByPlate(@PathVariable String plate){
    ResponseVehicleDto responseDto = new ResponseVehicleDto();
    
    try {
      responseDto.setData(Arrays.asList(service.findByPlate(plate)));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: found #" + responseDto.getData().size() + " vehicle.");
      
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
  public ResponseEntity<ResponseVehicleDto> updateParkingLot(@PathVariable Long id, @RequestBody @Validated RequestVehicleDto requestBody){
    ResponseVehicleDto responseDto = new ResponseVehicleDto();
    
    try {
      responseDto.setData(Arrays.asList(service.updateVehicle(requestBody, id)));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: update #" + responseDto.getData().size() + " vehicle.");
      
      return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    } catch (Exception e) {
      ErrorDetail error;
      
      if(e.getCause() instanceof ConstraintViolationException) {
        if(e.getMessage().contains("uk_plate")){
          error = new ErrorDetail("Unique constraint", "Plate: " + requestBody.getPlate() + " is already record for another user!");
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
  
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDeleteVehicleDto> delete(@PathVariable Long id){
    ResponseDeleteVehicleDto responseDto = new ResponseDeleteVehicleDto();
    
    try {
      responseDto.setData(service.deleteById(id));
      responseDto.setStatus(200);
      responseDto.setMessage("Success: delete vehicles.");
      
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
