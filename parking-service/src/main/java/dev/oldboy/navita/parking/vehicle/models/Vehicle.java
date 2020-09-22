package dev.oldboy.navita.parking.vehicle.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "navita_vehicles")
@XmlRootElement
public class Vehicle {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_vehicles")
  @SequenceGenerator(sequenceName = "seq_id_vehicles", allocationSize = 1, name = "seq_id_vehicles")
  @Column(name = "n_id_vehicle")
  private Long id;
  
  @NotBlank
  @Column(name = "s_brand")
  private String brand;
  
  @NotBlank
  @Column(name = "s_model")
  private String model;
  
  @NotBlank
  @Column(name = "s_color")
  private String color;

  @NotBlank
  @Column(name = "s_plate")
  private String plate;
  
  @NotNull
  @Column(name = "n_type")
  private Integer type;

  @Column(name = "t_updated_at")
  private LocalDateTime updateAt;
  
  @Column(name = "t_created_at")
  private LocalDateTime createdAt;
  
  public Vehicle() {}

  public Vehicle(Long id, @NotBlank String brand, @NotBlank String model, @NotBlank String color,
      @NotBlank String plate, @NotNull Integer type, LocalDateTime updateAt, LocalDateTime createdAt) {
    super();
    this.id = id;
    this.brand = brand;
    this.model = model;
    this.color = color;
    this.plate = plate;
    this.type = type;
    this.updateAt = updateAt;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public LocalDateTime getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(LocalDateTime updateAt) {
    this.updateAt = updateAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "Vehicle [id=" + id + ", brand=" + brand + ", model=" + model + ", color=" + color + ", plate=" + plate
        + ", type=" + type + ", updateAt=" + updateAt + ", createdAt=" + createdAt + "]";
  }

}
