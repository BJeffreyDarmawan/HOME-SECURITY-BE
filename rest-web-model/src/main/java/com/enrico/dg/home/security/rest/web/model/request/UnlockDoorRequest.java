package com.enrico.dg.home.security.rest.web.model.request;

import com.enrico.dg.home.security.entity.CommonModel;

public class UnlockDoorRequest extends CommonModel {

  private String messageType;
  private String message;
  private String command;

  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  @Override
  public String toString() {
    return "UnlockDoorRequest{" +
            "messageType='" + messageType + '\'' +
            ", message='" + message + '\'' +
            ", command='" + command + '\'' +
            '}';
  }
}
