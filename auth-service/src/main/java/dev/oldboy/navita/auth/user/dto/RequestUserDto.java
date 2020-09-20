package dev.oldboy.navita.auth.user.dto;

import javax.validation.constraints.NotBlank;

public class RequestUserDto {
  
  @NotBlank private String name;
  @NotBlank private String email;
  @NotBlank private String cpf;
  @NotBlank private String password;
  
  public RequestUserDto() {}
  
  public RequestUserDto(@NotBlank String name, @NotBlank String email, @NotBlank String cpf,
      @NotBlank String password) {
    super();
    this.name = name;
    this.email = email;
    this.cpf = cpf;
    this.password = password;
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

  @Override
  public String toString() {
    return "RequestUserDto [name=" + name + ", email=" + email + ", cpf=" + cpf + ", password=" + password + "]";
  }
}
