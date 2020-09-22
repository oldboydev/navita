package dev.oldboy.navita.parking.shared.dto;

import java.util.List;

import dev.oldboy.navita.parking.shared.models.ErrorDetail;

public class ResponseDto {
  protected Integer status;
  protected String message;
  protected List<ErrorDetail> errors;
  
  public ResponseDto() {}

  public ResponseDto(Integer status, String message, List<ErrorDetail> errors) {
    super();
    this.status = status;
    this.message = message;
    this.errors = errors;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<ErrorDetail> getErrors() {
    return errors;
  }

  public void setErrors(List<ErrorDetail> errors) {
    this.errors = errors;
  }

  @Override
  public String toString() {
    return "ResponseDTO [status=" + status + ", message=" + message + ", errors=" + errors + "]";
  }
}
