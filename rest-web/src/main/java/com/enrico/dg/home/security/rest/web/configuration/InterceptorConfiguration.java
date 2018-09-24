package com.enrico.dg.home.security.rest.web.configuration;

import com.enrico.dg.home.security.rest.web.component.InterceptorRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

  @Autowired
  InterceptorRequest interceptorRequest;

  public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void configureContentNegotiation(
      ContentNegotiationConfigurer contentNegotiationConfigurer) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void configureDefaultServletHandling(
      DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void addFormatters(FormatterRegistry formatterRegistry) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void addCorsMappings(CorsRegistry corsRegistry) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void configureMessageConverters(List<HttpMessageConverter<?>> list) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void extendMessageConverters(List<HttpMessageConverter<?>> list) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {
    //No Implementation Function, just following the WebMvcConfigurer Interface
  }

  public Validator getValidator() {
    return null;
  }

  public MessageCodesResolver getMessageCodesResolver() {
    return null;
  }

  public void addInterceptors(InterceptorRegistry interceptorRegistry) {
    interceptorRegistry.addInterceptor(interceptorRequest);
  }
}