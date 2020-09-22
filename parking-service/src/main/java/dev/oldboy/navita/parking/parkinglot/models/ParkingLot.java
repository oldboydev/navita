package dev.oldboy.navita.parking.parkinglot.models;

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
@Table(name = "navita_parking_lot")
@XmlRootElement
public class ParkingLot {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_parking_lot")
  @SequenceGenerator(sequenceName = "seq_id_parking_lot", allocationSize = 1, name = "seq_id_parking_lot")
  @Column(name = "n_id_parking_lot")
  private Long id;
  
  @NotBlank
  @Column(name = "s_name")
  private String name;
  
  @NotBlank
  @Column(name = "s_cnpj")
  private String cnpj;
  
  @NotBlank
  @Column(name = "s_address")
  private String address;
  
  @NotBlank
  @Column(name = "s_phone")
  private String phone;
  
  @NotNull
  @Column(name = "n_spaces_moto")
  private Integer spacesMoto;
  
  @NotNull
  @Column(name = "n_spaces_cars")
  private Integer spaceCars;

  @Column(name = "t_updated_at")
  private LocalDateTime updateAt;
  
  @Column(name = "t_created_at")
  private LocalDateTime createdAt;
  
  public ParkingLot() {}

  public ParkingLot(Long id, @NotBlank String name, @NotBlank String cnpj, @NotBlank String address,
      @NotBlank String phone, @NotBlank Integer spacesMoto, @NotBlank Integer spaceCars, LocalDateTime updateAt,
      LocalDateTime createdAt) {
    super();
    this.id = id;
    this.name = name;
    this.cnpj = cnpj;
    this.address = address;
    this.phone = phone;
    this.spacesMoto = spacesMoto;
    this.spaceCars = spaceCars;
    this.updateAt = updateAt;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    return "ParkingLot [id=" + id + ", name=" + name + ", cnpj=" + cnpj + ", address=" + address + ", phone=" + phone
        + ", spacesMoto=" + spacesMoto + ", spaceCars=" + spaceCars + ", updateAt=" + updateAt + ", createdAt="
        + createdAt + "]";
  }
  
}
