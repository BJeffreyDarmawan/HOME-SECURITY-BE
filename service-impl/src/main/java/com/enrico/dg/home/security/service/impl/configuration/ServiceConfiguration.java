package com.enrico.dg.home.security.service.impl.configuration;

import com.enrico.dg.home.security.libraries.configuration.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.enrico.dg.home.security.libraries.configuration.HomeSecurityMongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = "com.enrico.dg.home.security.dao")
@ComponentScan(basePackages = "com.enrico.dg.home.security.service")
@EnableMongoRepositories(value = "com.enrico.dg.home.security.dao")
@EnableMongoAuditing(auditorAwareRef = "stringAuditorAware")
public class ServiceConfiguration {

  @Bean
  public static DeserializationProblemHandler deserializationProblemHandler() {
    return new DeserializationProblemHandler();
  }

  private static final String DESCRIPTION = "mongodb";

  @Bean
  public MongoClientOptions mongoClientOptions(HomeSecurityMongoProperties
                                                       homeSecurityMongoProperties) {
    return new MongoClientOptions.Builder().writeConcern(WriteConcern.ACKNOWLEDGED)
        .connectionsPerHost(homeSecurityMongoProperties.getConnectionPerHost())
        .minConnectionsPerHost(homeSecurityMongoProperties.getMinConnectionsPerHost())
        .threadsAllowedToBlockForConnectionMultiplier(
            homeSecurityMongoProperties.getThreadsAllowedToBlockForConnectionMultiplier())
        .connectTimeout(homeSecurityMongoProperties.getConnectTimeout())
        .maxWaitTime(homeSecurityMongoProperties.getMaxWaitTime())
        .socketKeepAlive(homeSecurityMongoProperties.getSocketKeepAlive())
        .socketTimeout(homeSecurityMongoProperties.getSocketTimeout())
        .heartbeatConnectTimeout(homeSecurityMongoProperties.getHeartbeatConnectTimeout())
        .heartbeatFrequency(homeSecurityMongoProperties.getHeartbeatFrequency())
        .heartbeatSocketTimeout(homeSecurityMongoProperties.getHeartbeatSocketTimeout())
        .maxConnectionIdleTime(homeSecurityMongoProperties.getMaxConnectionIdleTime())
        .maxConnectionLifeTime(homeSecurityMongoProperties.getMaxConnectionLifeTime())
        .minHeartbeatFrequency(homeSecurityMongoProperties.getMinHeartbeatFrequency())
        .readPreference(ReadPreference.primary())
        .description(DESCRIPTION)
        .build();
  }

  @Bean
  public ObjectMapper createObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
    objectMapper.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT, false);
    objectMapper.addHandler(ServiceConfiguration.deserializationProblemHandler());
    return objectMapper;
  }

  @Bean
  public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoConverter converter) {
    return new MongoTemplate(mongoDbFactory, converter);
  }
}