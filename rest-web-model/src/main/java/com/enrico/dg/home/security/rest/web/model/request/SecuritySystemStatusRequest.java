package com.enrico.dg.home.security.rest.web.model.request;

import com.enrico.dg.home.security.entity.CommonModel;

public class SecuritySystemStatusRequest extends CommonModel {

  private Boolean isActive;

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  @Override
  public String toString() {
    return "SecuritySystemStatusRequest{" +
            "isActive=" + isActive +
            '}';
  }
}
