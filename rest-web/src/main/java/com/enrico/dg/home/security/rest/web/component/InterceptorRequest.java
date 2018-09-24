package com.enrico.dg.home.security.rest.web.component;

import com.enrico.dg.home.security.entity.constant.fields.BaseMongoFields;
import com.enrico.dg.home.security.rest.web.model.request.MandatoryRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enrico.dg.home.security.rest.web.model.request.MandatoryRequestBuilder;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class InterceptorRequest extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    MandatoryRequest mandatoryRequest = new MandatoryRequestBuilder()
        .withAccessToken(request.getHeader("accessToken"))
        .withChannelId(request.getHeader("channelId")).build();

    MDC.put("mandatoryRequest", mandatoryRequest);
    MDC.put(BaseMongoFields.CHANNEL_ID, request.getHeader("channelId"));
    MDC.put("accessToken", request.getHeader("accessToken"));

    request.setAttribute("mandatory", mandatoryRequest);

    return true;
  }
}