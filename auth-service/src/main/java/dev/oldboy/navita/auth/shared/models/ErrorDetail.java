package dev.oldboy.navita.auth.shared.models;

import java.util.Objects;

public class ErrorDetail {

    private String errorType;
    private String errorDetail;

    public ErrorDetail() {
    }

    public ErrorDetail(String errorType, String errorDetail) {
      this.errorType = errorType;
      this.errorDetail = errorDetail;
    }

    public String getErrorType() {
      return this.errorType;
    }

    public void setErrorType(String errorType) {
      this.errorType = errorType;
    }

    public String getErrorDetail() {
      return this.errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
      this.errorDetail = errorDetail;
    }

    public ErrorDetail errorType(String errorType) {
      this.errorType = errorType;
      return this;
    }

    public ErrorDetail errorDetail(String errorDetail) {
      this.errorDetail = errorDetail;
      return this;
    }

    @Override
      public boolean equals(Object o) {
          if (o == this)
              return true;
          if (!(o instanceof ErrorDetail)) {
              return false;
          }
          ErrorDetail errorData = (ErrorDetail) o;
          return Objects.equals(errorType, errorData.errorType) && Objects.equals(errorDetail, errorData.errorDetail);
    }

    @Override
    public int hashCode() {
      return Objects.hash(errorType, errorDetail);
    }

    @Override
    public String toString() {
      return "{" +
        " errorType='" + getErrorType() + "'" +
        ", errorDetail='" + getErrorDetail() + "'" +
        "}";
    }
}
