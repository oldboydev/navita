package dev.oldboy.navita.parking.vehicle.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dev.oldboy.navita.parking.shared.dto.ResponseDto;
import dev.oldboy.navita.parking.shared.models.ErrorDetail;

@XmlRootElement
public class ResponseDeleteVehicleDto extends ResponseDto {
  private Boolean data;
  
  public ResponseDeleteVehicleDto() {}

  public ResponseDeleteVehicleDto(Integer status, String message, List<ErrorDetail> errors, Boolean data) {
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
    return "ResponseDeleteVehicleDto [data=" + data + ", status=" + status + ", message=" + message + ", errors="
        + errors + "]";
  }
}
