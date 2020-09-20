package dev.oldboy.navita.gateway.dtos;

import java.util.List;

import dev.oldboy.navita.gateway.models.ErrorDetail;

public class ResponseDto {
  private Integer status;
  private String message;
  private Object data;
  private List<ErrorDetail> errors;
  
  public ResponseDto() {}

  public ResponseDto(Integer status, String message, Object data, List<ErrorDetail> errors) {
    super();
    this.status = status;
    this.message = message;
    this.data = data;
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

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public List<ErrorDetail> getErrors() {
    return errors;
  }

  public void setErrors(List<ErrorDetail> errors) {
    this.errors = errors;
  }

  @Override
  public String toString() {
    return "ResponseDTO [status=" + status + ", message=" + message + ", data=" + data + ", errors=" + errors + "]";
  }
}
