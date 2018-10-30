package com.enrico.dg.home.security.entity;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class JWTokenClaim {

  private String userId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "JWTokenClaim{" +
        "userId='" + userId + '\'' +
        '}';
  }
}
