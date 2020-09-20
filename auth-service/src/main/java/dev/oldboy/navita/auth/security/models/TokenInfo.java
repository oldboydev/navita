package dev.oldboy.navita.auth.security.models;

public class TokenInfo {
  private String token;
  private String type;
  private Integer expiresIn;
  
  public TokenInfo() {}
  
  public TokenInfo(String token, String type, Integer expiresIn) {
    super();
    this.token = token;
    this.type = type;
    this.expiresIn = expiresIn;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(Integer expiresIn) {
    this.expiresIn = expiresIn;
  }

  @Override
  public String toString() {
    return "Token [token=" + token + ", type=" + type + ", expiresIn=" + expiresIn + "]";
  }
  
}
