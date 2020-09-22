package dev.oldboy.navita.parking.manager.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RequestManagerDto {
  @NotNull private Long idParkingLot;
  @NotNull private Long idVehicle;
  //inform if is parking or leave
  @NotNull private Integer idOperation;
  
  public RequestManagerDto() {}

  public RequestManagerDto(@NotNull Long idParkingLot, @NotNull Long idVehicle, @NotNull Integer idOperation) {
    super();
    this.idParkingLot = idParkingLot;
    this.idVehicle = idVehicle;
    this.idOperation = idOperation;
  }

  public Long getIdParkingLot() {
    return idParkingLot;
  }

  public void setIdParkingLot(Long idParkingLot) {
    this.idParkingLot = idParkingLot;
  }

  public Long getIdVehicle() {
    return idVehicle;
  }

  public void setIdVehicle(Long idVehicle) {
    this.idVehicle = idVehicle;
  }

  public Integer getIdOperation() {
    return idOperation;
  }

  public void setIdOperation(Integer idOperation) {
    this.idOperation = idOperation;
  }

  @Override
  public String toString() {
    return "RequestManagerDto [idParkingLot=" + idParkingLot + ", idVehicle=" + idVehicle + ", idOperation="
        + idOperation + "]";
  }
}
