package com.enrico.dg.home.security.rest.web.controller;

import com.enrico.dg.home.security.entity.constant.ApiPath;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.SecuritySystem;
import com.enrico.dg.home.security.entity.dao.common.User;
import com.enrico.dg.home.security.libraries.utility.BaseResponseHelper;
import com.enrico.dg.home.security.rest.web.model.request.MandatoryRequest;
import com.enrico.dg.home.security.rest.web.model.request.SecuritySystemRequest;
import com.enrico.dg.home.security.rest.web.model.request.UserRequest;
import com.enrico.dg.home.security.rest.web.model.response.BaseResponse;
import com.enrico.dg.home.security.rest.web.model.response.SecuritySystemResponse;
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
  public BaseResponse<SecuritySystemResponse> initializeSystem(
          @RequestBody SecuritySystemRequest securitySystemRequest
  ) {

    SecuritySystem securitySystem = securitySystemService.initializeSystemStatus(toSecuritySystem(securitySystemRequest));

    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, toSecuritySystemResponse(securitySystem));
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
