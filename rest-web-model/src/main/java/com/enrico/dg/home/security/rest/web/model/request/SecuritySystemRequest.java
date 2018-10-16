package com.enrico.dg.home.security.rest.web.model.request;

import com.enrico.dg.home.security.entity.CommonModel;

public class SecuritySystemRequest extends CommonModel {

  private Boolean isActive;

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  @Override
  public String toString() {
    return "SecuritySystemRequest{" +
            "isActive=" + isActive +
            '}';
  }
}
