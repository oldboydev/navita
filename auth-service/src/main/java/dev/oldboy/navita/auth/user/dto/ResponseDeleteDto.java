package dev.oldboy.navita.auth.user.dto;

import java.util.List;

import dev.oldboy.navita.auth.shared.dtos.ResponseDto;
import dev.oldboy.navita.auth.shared.models.ErrorDetail;

public class ResponseDeleteDto extends ResponseDto {
  private Boolean data;

  public ResponseDeleteDto() {}

  public ResponseDeleteDto(Integer status, String message, List<ErrorDetail> errors, Boolean data) {
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
    return "ResponseDeleteDto [data=" + data + ", status=" + status + ", message=" + message + ", errors=" + errors
        + "]";
  }
}
