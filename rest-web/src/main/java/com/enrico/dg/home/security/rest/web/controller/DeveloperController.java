package com.enrico.dg.home.security.rest.web.controller;

import com.enrico.dg.home.security.entity.constant.ApiPath;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.SecuritySystemStatus;
import com.enrico.dg.home.security.entity.dao.common.User;
import com.enrico.dg.home.security.libraries.utility.BaseResponseHelper;
import com.enrico.dg.home.security.rest.web.model.request.MandatoryRequest;
import com.enrico.dg.home.security.rest.web.model.request.SecuritySystemStatusRequest;
import com.enrico.dg.home.security.rest.web.model.request.UserRequest;
import com.enrico.dg.home.security.rest.web.model.response.BaseResponse;
import com.enrico.dg.home.security.rest.web.model.response.SecuritySystemStatusResponse;
import com.enrico.dg.home.security.rest.web.model.response.UserResponse;
import com.enrico.dg.home.security.service.api.SecuritySystemService;
import com.enrico.dg.home.security.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(ApiPath.BASE_PATH + ApiPath.DEVELOPER)
public class DeveloperController {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeveloperController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private SecuritySystemService securitySystemService;

  @PostMapping(ApiPath.ADD_USER)
  public BaseResponse<UserResponse> addUser(
          @RequestBody UserRequest userRequest
  ) {

    User user = userService.register(toUser(userRequest));

    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, toUserResponse(user));
  }

  @PostMapping(ApiPath.INITIALIZE_SECURITY_SYSTEM)
  public BaseResponse<SecuritySystemStatusResponse> initializeSystem(
          @RequestBody SecuritySystemStatusRequest securitySystemStatusRequest
  ) {

    SecuritySystemStatus securitySystemStatus = securitySystemService.initializeSystemStatus(toSecuritySystem(securitySystemStatusRequest));

    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, toSecuritySystemResponse(securitySystemStatus));
  }

  private User toUser(UserRequest userRequest) {
    User user = new User();
    user.setPassword(userRequest.getPassword());
    user.setEmail(userRequest.getEmail());
    user.setName(userRequest.getName());
    user.setRole(userRequest.getRole());
    user.setMacAddress(userRequest.getMacAddress());
    user.setSosNumber(userRequest.getSosNumber());
    user.setEmergencyNumber(userRequest.getEmergencyNumber());

    return user;
  }

  private UserResponse toUserResponse(User user) {
    if(user == null) {
      return null;
    }

    UserResponse userResponse = new UserResponse();
    userResponse.setEmail(user.getEmail());
    userResponse.setName(user.getName());
    userResponse.setRole(user.getRole());
    userResponse.setPassword(user.getPassword());
    userResponse.setId(user.getId());
    userResponse.setMacAddress(user.getMacAddress());
    userResponse.setSosNumber(user.getSosNumber());
    userResponse.setEmergencyNumber(user.getEmergencyNumber());

    return userResponse;
  }

  private SecuritySystemStatus toSecuritySystem(SecuritySystemStatusRequest securitySystemStatusRequest) {
    SecuritySystemStatus securitySystemStatus = new SecuritySystemStatus();
    securitySystemStatus.setActive(securitySystemStatusRequest.getActive());

    return securitySystemStatus;
  }

  private SecuritySystemStatusResponse toSecuritySystemResponse(SecuritySystemStatus securitySystemStatus) {
    if(securitySystemStatus == null) {
      return null;
    }

    SecuritySystemStatusResponse securitySystemStatusResponse = new SecuritySystemStatusResponse();
    securitySystemStatusResponse.setActive(securitySystemStatus.getActive());

    return securitySystemStatusResponse;
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }
}
