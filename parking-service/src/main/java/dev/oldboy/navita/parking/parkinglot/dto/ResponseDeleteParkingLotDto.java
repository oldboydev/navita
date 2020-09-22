package dev.oldboy.navita.parking.parkinglot.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dev.oldboy.navita.parking.shared.dto.ResponseDto;
import dev.oldboy.navita.parking.shared.models.ErrorDetail;

@XmlRootElement
public class ResponseDeleteParkingLotDto extends ResponseDto{
  private Boolean data;
  
  public ResponseDeleteParkingLotDto() {}

  public ResponseDeleteParkingLotDto(Integer status, String message, List<ErrorDetail> errors, Boolean data) {
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
    return "ResponseDeleteParkingLotDto [data=" + data + ", status=" + status + ", message=" + message + ", errors="
        + errors + "]";
  }
}
