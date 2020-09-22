package dev.oldboy.navita.parking.vehicle.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RequestVehicleDto {
  @NotBlank private String brand;
  @NotBlank private String model;
  @NotBlank private String color;
  @NotBlank private String plate;
  @NotNull private Integer type;
  
  public RequestVehicleDto() {}

  public RequestVehicleDto(@NotBlank String brand, @NotBlank String model, @NotBlank String color,
      @NotBlank String plate, @NotNull Integer type) {
    super();
    this.brand = brand;
    this.model = model;
    this.color = color;
    this.plate = plate;
    this.type = type;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getPlate() {
    return plate;
  }

  public void setPlate(String plate) {
    this.plate = plate;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "RequestVehicleLotDto [brand=" + brand + ", model=" + model + ", color=" + color + ", plate=" + plate
        + ", type=" + type + "]";
  }
}
