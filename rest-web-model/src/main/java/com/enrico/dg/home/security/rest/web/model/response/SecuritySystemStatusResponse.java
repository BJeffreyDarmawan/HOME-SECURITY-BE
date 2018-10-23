package com.enrico.dg.home.security.rest.web.model.response;

import com.enrico.dg.home.security.entity.CommonModel;

public class SecuritySystemStatusResponse extends CommonModel {

  private Boolean isActive;

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  @Override
  public String toString() {
    return "SecuritySystemStatusResponse{" +
            "isActive=" + isActive +
            '}';
  }
}
