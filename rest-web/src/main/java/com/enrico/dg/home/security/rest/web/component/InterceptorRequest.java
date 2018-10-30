package com.enrico.dg.home.security.rest.web.component;

import com.enrico.dg.home.security.rest.web.model.request.MandatoryRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enrico.dg.home.security.rest.web.model.request.MandatoryRequestBuilder;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class InterceptorRequest extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withAccessToken(request.getHeader("accessToken"))
        .build();

    MDC.put("mandatoryRequest", mandatoryRequest);
    MDC.put("accessToken", request.getHeader("accessToken"));

    request.setAttribute("mandatory", mandatoryRequest);

    return true;
  }
}