package dev.oldboy.navita.auth.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "navita_users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_user")
  @SequenceGenerator(sequenceName = "seq_id_user", allocationSize = 1, name = "seq_id_user")
  @Column(name = "n_id_user")
  private Long id;
  
  @NotBlank
  @Column(name = "s_name")
  private String name;
  
  @NotBlank
  
  @Column(name = "s_email")
  private String email;
  
  @NotBlank
  @Column(name = "s_cpf")
  private String cpf;
  
  @NotBlank
  @Column(name = "s_password")
  private String password;
  
  @Column(name = "t_updated_at")
  private LocalDateTime updatedAt;
  
  @Column(name = "t_created_at")
  private LocalDateTime createdAt;
  
  public User() {}

  public User(Long id, @NotBlank String name, @NotBlank String email, @NotBlank String cpf, @NotBlank String password,
      LocalDateTime updatedAt, LocalDateTime createdAt) {
    super();
    this.id = id;
    this.name = name;
    this.email = email;
    this.cpf = cpf;
    this.password = password;
    this.updatedAt = updatedAt;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", email=" + email + ", cpf=" + cpf + ", password=" + password
        + ", updatedAt=" + updatedAt + ", createdAt=" + createdAt + "]";
  } 
}
