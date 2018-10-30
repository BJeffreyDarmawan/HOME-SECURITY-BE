package com.enrico.dg.home.security.libraries.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

  private static final String STRING = "string";
  private static final String HEADER = "header";

  @Bean
  public Docket init() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.enrico.dg.home.security.rest.web.controller"))
        .paths(regex("/.*"))
        .build()
        .globalOperationParameters(Arrays.asList(
            new ParameterBuilder().name("accessToken").parameterType(HEADER)
                .modelRef(new ModelRef(STRING)).required(true).defaultValue("TOKEN")
                .description("client's access token").build()))
        .genericModelSubstitutes(DeferredResult.class, ResponseEntity.class);
  }
}