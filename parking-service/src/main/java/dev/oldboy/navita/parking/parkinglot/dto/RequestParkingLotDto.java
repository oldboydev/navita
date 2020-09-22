package dev.oldboy.navita.parking.parkinglot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RequestParkingLotDto{
  @NotBlank private String name; 
  @NotBlank private String cnpj;
  @NotBlank private String address;
  @NotBlank private String phone;
  @NotNull private Integer spacesMoto;
  @NotNull private Integer spaceCars;
  
  public RequestParkingLotDto() {}

  public RequestParkingLotDto(@NotBlank String name, @NotBlank String cnpj, @NotBlank String address,
      @NotBlank String phone, @NotNull Integer spacesMoto, @NotNull Integer spaceCars) {
    super();
    this.name = name;
    this.cnpj = cnpj;
    this.address = address;
    this.phone = phone;
    this.spacesMoto = spacesMoto;
    this.spaceCars = spaceCars;
  }



  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getSpacesMoto() {
    return spacesMoto;
  }

  public void setSpacesMoto(Integer spacesMoto) {
    this.spacesMoto = spacesMoto;
  }

  public Integer getSpaceCars() {
    return spaceCars;
  }

  public void setSpaceCars(Integer spaceCars) {
    this.spaceCars = spaceCars;
  }

  @Override
  public String toString() {
    return "RequestParkingLotDto [name=" + name + ", cnpj=" + cnpj + ", address=" + address + ", phone=" + phone
        + ", spacesMoto=" + spacesMoto + ", spaceCars=" + spaceCars + "]";
  }
}
