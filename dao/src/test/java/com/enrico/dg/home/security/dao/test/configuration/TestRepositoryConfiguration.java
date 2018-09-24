package com.enrico.dg.home.security.dao.test.configuration;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.enrico.dg.home.security.entity",
    "com.enrico.dg.home.security.dao"})
@EnableMongoAuditing(auditorAwareRef = "stringAuditorAware")
@ComponentScan(basePackages = {"com.enrico.dg.home.security.entity",
    "com.enrico.dg.home.security.dao"})
public class TestRepositoryConfiguration extends AbstractMongoConfiguration {

  public static final Mongo createNewMongo() {
    Fongo fongo = new Fongo("fongo");
    Mongo mongo = fongo.getMongo();
    return mongo;
  }

  private static final String ENTITY = "com.enrico.dg.home.security.entity";
  private static final String DATABASE_NAME = "testing";

  private static final Mongo MONGO = createNewMongo();

  @Override
  protected String getDatabaseName() {
    return this.DATABASE_NAME;
  }

  @Override
  protected Collection<String> getMappingBasePackages() {
    return Arrays.asList(this.ENTITY);
  }

  @Override
  public Mongo mongo() throws Exception {
    return this.MONGO;
  }

  @Override
  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
    return new MongoTemplate(this.mongo(), this.DATABASE_NAME);
  }

  @Bean
  public AuditorAware<String> stringAuditorAware() {
    return () -> "system";
  }
}
