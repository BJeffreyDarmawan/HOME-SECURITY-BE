package com.enrico.dg.home.security.rest.web.model.response;

import com.enrico.dg.home.security.entity.CommonModel;

import java.util.Map;

public class UnlockDoorResponse extends CommonModel {

  private Map sensorsFeedback;

  public Map getSensorsFeedback() {
    return sensorsFeedback;
  }

  public void setSensorsFeedback(Map sensorsFeedback) {
    this.sensorsFeedback = sensorsFeedback;
  }

  @Override
  public String toString() {
    return "UnlockDoorResponse{" +
            "sensorsFeedback=" + sensorsFeedback +
            '}';
  }
}
