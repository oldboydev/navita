package dev.oldboy.navita.parking.vehicle.dtos;

import java.util.List;

import dev.oldboy.navita.parking.shared.dto.ResponseDto;
import dev.oldboy.navita.parking.shared.models.ErrorDetail;

public class ResponseConfirmDto extends ResponseDto {
  private Boolean data;
  
  public ResponseConfirmDto() {}

  public ResponseConfirmDto(Integer status, String message, List<ErrorDetail> errors, Boolean data) {
    super(status, message, errors);
    this.data = data;
  }

  public Boolean getData() {
    return data;
  }

  public void setData(Boolean data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "ResponseConfirmDto [data=" + data + ", status=" + status + ", message=" + message + ", errors=" + errors
        + "]";
  }
}
