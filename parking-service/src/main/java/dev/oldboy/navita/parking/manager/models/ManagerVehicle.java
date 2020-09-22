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

@Entity
@Table(name = "navita_manager_vehicle")
public class ManagerVehicle {
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_manager_vehicle")
  @SequenceGenerator(sequenceName = "seq_id_manager_vehicle", allocationSize = 1, name = "seq_id_manager_vehicle")
  @Column(name = "n_id_manager_vehicle")
  private Long id;
  
  @NotNull
  @Column(name = "n_id_manager")
  private Long idManager;
  
  @NotNull
  @Column(name = "n_id_vehicle")
  private Long idVehicle;
 
  @Column(name = "t_updated_at")
  private LocalDateTime updateAt;
  
  @Column(name = "t_created_at")
  private LocalDateTime createdAt;
  
  public ManagerVehicle() {}

  public ManagerVehicle(Long id, @NotNull Long idManager, @NotNull Long idVehicle, LocalDateTime updateAt,
      LocalDateTime createdAt) {
    super();
    this.id = id;
    this.idManager = idManager;
    this.idVehicle = idVehicle;
    this.updateAt = updateAt;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIdManager() {
    return idManager;
  }

  public void setIdManager(Long idManager) {
    this.idManager = idManager;
  }

  public Long getIdVehicle() {
    return idVehicle;
  }

  public void setIdVehicle(Long idVehicle) {
    this.idVehicle = idVehicle;
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
    return "ManagerVehicle [id=" + id + ", idManager=" + idManager + ", idVehicle=" + idVehicle + ", updateAt="
        + updateAt + ", createdAt=" + createdAt + "]";
  }
}
