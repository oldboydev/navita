package dev.oldboy.navita.auth.security.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dev.oldboy.navita.auth.security.models.TokenInfo;
import dev.oldboy.navita.auth.shared.dtos.ResponseDto;
import dev.oldboy.navita.auth.shared.models.ErrorDetail;

@XmlRootElement(name = "response")
public class ResponseAuthDto extends ResponseDto{
  private TokenInfo data;

  public ResponseAuthDto() {}

  public ResponseAuthDto(Integer status, String message, TokenInfo data, List<ErrorDetail> errors) {
    super(status, message, errors);
    this.data = data;
  }

  public TokenInfo getData() {
    return data;
  }

  public void setData(TokenInfo data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "ResponseAuthDto [data=" + data + ", status=" + status + ", message=" + message + ", errors=" + errors + "]";
  }
  
}
