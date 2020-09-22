package dev.oldboy.navita.parking.manager.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "navita_manager")
@XmlRootElement
public class Manager {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_manager")
  @SequenceGenerator(sequenceName = "seq_id_manager", allocationSize = 1, name = "seq_id_manager")
  @Column(name = "n_id_manager")
  private Long id;
  
  @NotNull
  @Column(name = "n_id_parking_lot")
  private Long idParkingLot;
  
  @NotNull
  @Column(name = "n_spaces_moto")
  private Integer spacesMoto;
  
  @NotNull
  @Column(name = "n_spaces_cars")
  private Integer spaceCars;
  
  @NotNull
  @Column(name = "n_spaces_moto_empty")
  private Integer spacesMotoEmpty;
  
  @NotNull
  @Column(name = "n_spaces_cars_empty")
  private Integer spaceCarsEmpty;

  @Column(name = "t_updated_at")
  private LocalDateTime updateAt;
  
  @Column(name = "t_created_at")
  private LocalDateTime createdAt;
  
  public Manager() {}

  public Manager(Long id, @NotNull Long idParkingLot, @NotNull Integer spacesMoto, @NotNull Integer spaceCars,
      @NotNull Integer spacesMotoEmpty, @NotNull Integer spaceCarsEmpty, LocalDateTime updateAt,
      LocalDateTime createdAt) {
    super();
    this.id = id;
    this.idParkingLot = idParkingLot;
    this.spacesMoto = spacesMoto;
    this.spaceCars = spaceCars;
    this.spacesMotoEmpty = spacesMotoEmpty;
    this.spaceCarsEmpty = spaceCarsEmpty;
    this.updateAt = updateAt;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIdParkingLot() {
    return idParkingLot;
  }

  public void setIdParkingLot(Long idParkingLot) {
    this.idParkingLot = idParkingLot;
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

  public Integer getSpacesMotoEmpty() {
    return spacesMotoEmpty;
  }

  public void setSpacesMotoEmpty(Integer spacesMotoEmpty) {
    this.spacesMotoEmpty = spacesMotoEmpty;
  }

  public Integer getSpaceCarsEmpty() {
    return spaceCarsEmpty;
  }

  public void setSpaceCarsEmpty(Integer spaceCarsEmpty) {
    this.spaceCarsEmpty = spaceCarsEmpty;
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
    return "Manager [id=" + id + ", idParkingLot=" + idParkingLot + ", spacesMoto=" + spacesMoto + ", spaceCars="
        + spaceCars + ", spacesMotoEmpty=" + spacesMotoEmpty + ", spaceCarsEmpty=" + spaceCarsEmpty + ", updateAt="
        + updateAt + ", createdAt=" + createdAt + "]";
  }
}
