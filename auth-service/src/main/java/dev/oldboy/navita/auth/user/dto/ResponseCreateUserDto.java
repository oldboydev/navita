package dev.oldboy.navita.auth.user.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dev.oldboy.navita.auth.shared.dtos.ResponseDto;
import dev.oldboy.navita.auth.shared.models.ErrorDetail;
import dev.oldboy.navita.auth.user.models.User;

@XmlRootElement(name = "response")
public class ResponseCreateUserDto extends ResponseDto{
  private User data;

  public ResponseCreateUserDto() {}

  public ResponseCreateUserDto(Integer status, String message, List<ErrorDetail> errors, User user) {
    super(status, message, errors);
    this.data = user;
  }

  public User getData() {
    return data;
  }

  public void setData(User data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "ResponseCreateUserDto [user=" + data + ", status=" + status + ", message=" + message + ", errors=" + errors
        + "]";
  }
 
}
