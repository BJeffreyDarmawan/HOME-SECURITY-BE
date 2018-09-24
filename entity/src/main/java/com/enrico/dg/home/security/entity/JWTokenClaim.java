package com.enrico.dg.home.security.entity;

import net.karneim.pojobuilder.GeneratePojoBuilder;

@GeneratePojoBuilder
public class JWTokenClaim {

  private String userId;
  private Integer userLevel;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getUserLevel() {
    return userLevel;
  }

  public void setUserLevel(Integer userLevel) {
    this.userLevel = userLevel;
  }

  @Override
  public String toString() {
    return "JWTokenClaim{" +
        "userId='" + userId + '\'' +
        ", userLevel=" + userLevel +
        '}';
  }
}
