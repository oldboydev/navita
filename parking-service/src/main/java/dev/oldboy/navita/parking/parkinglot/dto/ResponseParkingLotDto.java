package dev.oldboy.navita.parking.parkinglot.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dev.oldboy.navita.parking.parkinglot.models.ParkingLot;
import dev.oldboy.navita.parking.shared.dto.ResponseDto;
import dev.oldboy.navita.parking.shared.models.ErrorDetail;

@XmlRootElement(name = "response")
public class ResponseParkingLotDto extends ResponseDto{
  private List<ParkingLot> data;
  
  public ResponseParkingLotDto() {}

  public ResponseParkingLotDto(Integer status, String message, List<ErrorDetail> errors, List<ParkingLot> data) {
    super(status, message, errors);
    this.data = data;
  }

  public List<ParkingLot> getData() {
    return data;
  }

  public void setData(List<ParkingLot> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "ResponseParkingLotDto [data=" + data + ", status=" + status + ", message=" + message + ", errors=" + errors
        + "]";
  }

}
  
