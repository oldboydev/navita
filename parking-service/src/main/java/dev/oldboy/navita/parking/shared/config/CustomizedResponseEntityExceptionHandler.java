package dev.oldboy.navita.parking.shared.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dev.oldboy.navita.parking.shared.dto.ResponseDto;
import dev.oldboy.navita.parking.shared.models.ErrorDetail;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
  
  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<ResponseDto> handleAnyException(Exception ex, WebRequest request) {
    String errorMessageDescription = ex.getLocalizedMessage();

    if (errorMessageDescription == null) {
      errorMessageDescription = ex.toString();
    }

    ErrorDetail error = new ErrorDetail("Validation Failed", errorMessageDescription);
    List<ErrorDetail> errors = new ArrayList<>();
    errors.add(error);
    
    ResponseDto response = new ResponseDto();
    response.setMessage("Error: see error details for more info");
    response.setStatus(400);
    response.setErrors(errors);
    
    return ResponseEntity.badRequest().body(response);
  }
}
