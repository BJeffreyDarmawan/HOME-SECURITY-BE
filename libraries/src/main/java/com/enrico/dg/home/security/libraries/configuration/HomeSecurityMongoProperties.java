package com.enrico.dg.home.security.libraries.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("home.security.mongo")
public class HomeSecurityMongoProperties {
  private Integer connectionPerHost;
  private Integer minConnectionsPerHost;
  private Integer threadsAllowedToBlockForConnectionMultiplier;
  private Integer connectTimeout;
  private Integer maxWaitTime;
  private Integer socketTimeout;
  private Boolean socketKeepAlive;
  private Integer heartbeatConnectTimeout;
  private Integer heartbeatFrequency;
  private Integer heartbeatSocketTimeout;
  private Integer maxConnectionIdleTime;
  private Integer maxConnectionLifeTime;
  private Integer minHeartbeatFrequency;
  private String readPreference;
  private Integer writeNumber;

  public Integer getConnectionPerHost() {
    return this.connectionPerHost;
  }

  public Integer getConnectTimeout() {
    return this.connectTimeout;
  }

  public Integer getHeartbeatConnectTimeout() {
    return this.heartbeatConnectTimeout;
  }

  public Integer getHeartbeatFrequency() {
    return this.heartbeatFrequency;
  }

  public Integer getHeartbeatSocketTimeout() {
    return this.heartbeatSocketTimeout;
  }

  public Integer getMaxConnectionIdleTime() {
    return this.maxConnectionIdleTime;
  }

  public Integer getMaxConnectionLifeTime() {
    return this.maxConnectionLifeTime;
  }

  public Integer getMaxWaitTime() {
    return this.maxWaitTime;
  }

  public Integer getMinConnectionsPerHost() {
    return this.minConnectionsPerHost;
  }

  public Integer getMinHeartbeatFrequency() {
    return this.minHeartbeatFrequency;
  }

  public String getReadPreference() {
    return this.readPreference;
  }

  public Boolean getSocketKeepAlive() {
    return this.socketKeepAlive;
  }

  public Integer getSocketTimeout() {
    return this.socketTimeout;
  }

  public Integer getThreadsAllowedToBlockForConnectionMultiplier() {
    return this.threadsAllowedToBlockForConnectionMultiplier;
  }

  public Integer getWriteNumber() {
    return this.writeNumber;
  }

  public void setConnectionPerHost(Integer connectionPerHost) {
    this.connectionPerHost = connectionPerHost;
  }

  public void setConnectTimeout(Integer connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public void setHeartbeatConnectTimeout(Integer heartbeatConnectTimeout) {
    this.heartbeatConnectTimeout = heartbeatConnectTimeout;
  }

  public void setHeartbeatFrequency(Integer heartbeatFrequency) {
    this.heartbeatFrequency = heartbeatFrequency;
  }

  public void setHeartbeatSocketTimeout(Integer heartbeatSocketTimeout) {
    this.heartbeatSocketTimeout = heartbeatSocketTimeout;
  }

  public void setMaxConnectionIdleTime(Integer maxConnectionIdleTime) {
    this.maxConnectionIdleTime = maxConnectionIdleTime;
  }

  public void setMaxConnectionLifeTime(Integer maxConnectionLifeTime) {
    this.maxConnectionLifeTime = maxConnectionLifeTime;
  }

  public void setMaxWaitTime(Integer maxWaitTime) {
    this.maxWaitTime = maxWaitTime;
  }

  public void setMinConnectionsPerHost(Integer minConnectionsPerHost) {
    this.minConnectionsPerHost = minConnectionsPerHost;
  }

  public void setMinHeartbeatFrequency(Integer minHeartbeatFrequency) {
    this.minHeartbeatFrequency = minHeartbeatFrequency;
  }

  public void setReadPreference(String readPreference) {
    this.readPreference = readPreference;
  }

  public void setSocketKeepAlive(Boolean socketKeepAlive) {
    this.socketKeepAlive = socketKeepAlive;
  }

  public void setSocketTimeout(Integer socketTimeout) {
    this.socketTimeout = socketTimeout;
  }

  public void setThreadsAllowedToBlockForConnectionMultiplier(
      Integer threadsAllowedToBlockForConnectionMultiplier) {
    this.threadsAllowedToBlockForConnectionMultiplier =
        threadsAllowedToBlockForConnectionMultiplier;
  }

  public void setWriteNumber(Integer writeNumber) {
    this.writeNumber = writeNumber;
  }
}
