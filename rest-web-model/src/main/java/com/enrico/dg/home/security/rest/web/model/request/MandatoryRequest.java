package com.enrico.dg.home.security.rest.web.model.request;

import java.io.Serializable;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

@GeneratePojoBuilder
public class MandatoryRequest implements Serializable {

  @NotBlank
  private String accessToken;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @Override
  public String toString() {
    return "MandatoryRequest{" +
        ", accessToken='" + accessToken + '\'' +
        '}';
  }
}
