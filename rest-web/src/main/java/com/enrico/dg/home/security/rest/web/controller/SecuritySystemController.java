package com.enrico.dg.home.security.rest.web.controller;

import com.cloudinary.utils.ObjectUtils;
import com.enrico.dg.home.security.entity.constant.ApiPath;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.SecuritySystemStatus;
import com.enrico.dg.home.security.libraries.utility.BaseResponseHelper;
import com.enrico.dg.home.security.rest.web.model.request.MandatoryRequest;
import com.enrico.dg.home.security.rest.web.model.request.SecuritySystemStatusRequest;
import com.enrico.dg.home.security.rest.web.model.request.UnlockDoorRequest;
import com.enrico.dg.home.security.rest.web.model.response.BaseResponse;
import com.enrico.dg.home.security.rest.web.model.response.SecuritySystemStatusResponse;
import com.enrico.dg.home.security.rest.web.model.response.UnlockDoorResponse;
import com.enrico.dg.home.security.service.api.AuthService;
import com.enrico.dg.home.security.service.api.SecuritySystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(ApiPath.BASE_PATH + ApiPath.SECURITY_SYSTEM)
public class SecuritySystemController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SecuritySystemController.class);

  @Autowired
  private AuthService authService;

  @Autowired
  private SecuritySystemService securitySystemService;

  @GetMapping
  private BaseResponse<SecuritySystemStatusResponse> get (
          @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest
  ) {

    authService.isTokenValid(mandatoryRequest.getAccessToken());

    SecuritySystemStatus securitySystemStatus = securitySystemService.findSystemStatus();

    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, toSecuritySystemStatusResponse(securitySystemStatus));
  }

  @PutMapping
  private BaseResponse<SecuritySystemStatusResponse> update (
          @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
          @RequestBody SecuritySystemStatusRequest securitySystemStatusRequest
  ) {

    authService.isTokenValid(mandatoryRequest.getAccessToken());

    SecuritySystemStatus securitySystemStatus = securitySystemService.updateSystemStatus(toSecuritySystemStatus(securitySystemStatusRequest));

    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, toSecuritySystemStatusResponse(securitySystemStatus));
  }

//  @PostMapping(ApiPath.DOOR_SENSORS_MESSAGE)
//  private BaseResponse<UnlockDoorResponse> unlockDoorMessage (
//          @RequestBody UnlockDoorRequest unlockDoorRequest
//  ) {
//
//    UnlockDoorResponse unlockDoorResponse = toUnlockDoorResponse(unlockDoorRequest);
//    LOGGER.info(unlockDoorResponse.getSensorsFeedback().toString());
//
//    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
//            null, unlockDoorResponse);
//  }

  private SecuritySystemStatus toSecuritySystemStatus(SecuritySystemStatusRequest securitySystemStatusRequest) {
    SecuritySystemStatus securitySystemStatus = new SecuritySystemStatus();
    securitySystemStatus.setActive(securitySystemStatusRequest.getActive());

    return securitySystemStatus;
  }

  private SecuritySystemStatusResponse toSecuritySystemStatusResponse(SecuritySystemStatus securitySystemStatus) {
    if(securitySystemStatus == null) {
      return null;
    }

    SecuritySystemStatusResponse securitySystemStatusResponse = new SecuritySystemStatusResponse();
    securitySystemStatusResponse.setActive(securitySystemStatus.getActive());

    return securitySystemStatusResponse;
  }

  private UnlockDoorResponse toUnlockDoorResponse(UnlockDoorRequest unlockDoorRequest) {

    Map unlockDoorMessageMap = ObjectUtils.asMap(
            "messageType", unlockDoorRequest.getMessageType(),
            "message", unlockDoorRequest.getMessage(),
            "command", unlockDoorRequest.getCommand());

    UnlockDoorResponse unlockDoorResponse = new UnlockDoorResponse();
    unlockDoorResponse.setSensorsFeedback(unlockDoorMessageMap);

    return unlockDoorResponse;
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }
}
