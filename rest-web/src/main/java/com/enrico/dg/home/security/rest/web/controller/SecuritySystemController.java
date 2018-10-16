package com.enrico.dg.home.security.rest.web.controller;

import com.enrico.dg.home.security.entity.constant.ApiPath;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.SecuritySystem;
import com.enrico.dg.home.security.libraries.utility.BaseResponseHelper;
import com.enrico.dg.home.security.rest.web.model.request.MandatoryRequest;
import com.enrico.dg.home.security.rest.web.model.request.SecuritySystemRequest;
import com.enrico.dg.home.security.rest.web.model.response.BaseResponse;
import com.enrico.dg.home.security.rest.web.model.response.SecuritySystemResponse;
import com.enrico.dg.home.security.service.api.AuthService;
import com.enrico.dg.home.security.service.api.SecuritySystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(ApiPath.BASE_PATH + ApiPath.SECURITY_SYSTEM)
public class SecuritySystemController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SecuritySystemController.class);

  @Autowired
  private AuthService authService;

  @Autowired
  private SecuritySystemService securitySystemService;

  @GetMapping
  private BaseResponse<SecuritySystemResponse> get (
          @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest
  ) {

    authService.isTokenValid(mandatoryRequest.getAccessToken());

    SecuritySystem securitySystem = securitySystemService.findSystemStatus();

    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, toSecuritySystemResponse(securitySystem));
  }

  @PutMapping
  private BaseResponse<SecuritySystemResponse> update (
          @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
          @RequestBody SecuritySystemRequest securitySystemRequest
  ) {

    authService.isTokenValid(mandatoryRequest.getAccessToken());

    SecuritySystem securitySystem = securitySystemService.updateSystemStatus(toSecuritySystem(securitySystemRequest));

    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, toSecuritySystemResponse(securitySystem));
  }

  private SecuritySystem toSecuritySystem(SecuritySystemRequest securitySystemRequest) {
    SecuritySystem securitySystem = new SecuritySystem();
    securitySystem.setActive(securitySystemRequest.getActive());

    return  securitySystem;
  }

  private SecuritySystemResponse toSecuritySystemResponse(SecuritySystem securitySystem) {
    if(securitySystem == null) {
      return null;
    }

    SecuritySystemResponse securitySystemResponse = new SecuritySystemResponse();
    securitySystemResponse.setActive(securitySystem.getActive());

    return securitySystemResponse;
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }
}
