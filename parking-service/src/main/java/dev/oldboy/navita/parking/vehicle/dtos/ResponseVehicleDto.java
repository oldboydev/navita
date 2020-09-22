package dev.oldboy.navita.parking.vehicle.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dev.oldboy.navita.parking.shared.dto.ResponseDto;
import dev.oldboy.navita.parking.shared.models.ErrorDetail;
import dev.oldboy.navita.parking.vehicle.models.Vehicle;

@XmlRootElement
public class ResponseVehicleDto extends ResponseDto{
  private List<Vehicle> data;
  
  public ResponseVehicleDto() {}

  public ResponseVehicleDto(Integer status, String message, List<ErrorDetail> errors, List<Vehicle> data) {
    super(status, message, errors);
    this.data = data;
  }

  public List<Vehicle> getData() {
    return data;
  }

  public void setData(List<Vehicle> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "ResponseVehicleDto [data=" + data + ", status=" + status + ", message=" + message + ", errors=" + errors
        + "]";
  }
}
