package com.enrico.dg.home.security.rest.web.model.response;

import com.enrico.dg.home.security.entity.CommonModel;

public class SecuritySystemResponse extends CommonModel {

  private Boolean isActive;

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  @Override
  public String toString() {
    return "SecuritySystemResponse{" +
            "isActive=" + isActive +
            '}';
  }
}
