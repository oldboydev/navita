package dev.oldboy.navita.auth.user.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dev.oldboy.navita.auth.shared.dtos.ResponseDto;
import dev.oldboy.navita.auth.shared.models.ErrorDetail;
import dev.oldboy.navita.auth.user.models.User;

@XmlRootElement
public class ResponseUserDto extends ResponseDto{
  
  private List<User> data;

  public ResponseUserDto() {}

  public ResponseUserDto(Integer status, String message, List<ErrorDetail> errors, List<User> data) {
    super(status, message, errors);
    this.data = data;
  }

  public List<User> getData() {
    return data;
  }

  public void setData(List<User> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "ResponseUserDto [data=" + data + ", status=" + status + ", message=" + message + ", errors=" + errors + "]";
  }
}
