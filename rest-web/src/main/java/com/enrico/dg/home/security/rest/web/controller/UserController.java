package com.enrico.dg.home.security.rest.web.controller;

import com.enrico.dg.home.security.entity.constant.ApiPath;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.User;
import com.enrico.dg.home.security.libraries.exception.BusinessLogicException;
import com.enrico.dg.home.security.libraries.utility.BaseResponseHelper;
import com.enrico.dg.home.security.libraries.utility.PasswordHelper;
import com.enrico.dg.home.security.rest.web.model.request.LoginRequest;
import com.enrico.dg.home.security.rest.web.model.request.MandatoryRequest;
import com.enrico.dg.home.security.rest.web.model.request.UserRequest;
import com.enrico.dg.home.security.rest.web.model.response.BaseResponse;
import com.enrico.dg.home.security.rest.web.model.response.UserResponse;
import com.enrico.dg.home.security.service.api.AuthService;
import com.enrico.dg.home.security.service.api.CacheService;
import com.enrico.dg.home.security.service.api.UserService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(ApiPath.BASE_PATH + ApiPath.USER)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private CacheService cacheService;

    @PostMapping(value = ApiPath.ADD_USER)
    public BaseResponse<UserResponse> addUser(
            @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
            @RequestBody UserRequest userRequest
    ) {

        authService.isTokenValid(mandatoryRequest.getAccessToken());

        User user = userService.register(toUser(userRequest));

        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, toUserResponse(user));
    }

    @PostMapping(ApiPath.SIGN_IN)
    public BaseResponse<UserResponse> signIn(@RequestBody LoginRequest loginRequest) {

      User user = userService.login(loginRequest.getEmail());

      if(PasswordHelper.matchPassword(loginRequest.getPassword(), user.getPassword())) {

        String token = authService.createToken(user.getId());

        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, toUserResponse(user, token));
      } else {
        throw new BusinessLogicException(ResponseCode.INVALID_PASSWORD.getCode(),
                ResponseCode.INVALID_PASSWORD.getMessage());
      }
    }

    @GetMapping(ApiPath.ID)
    public BaseResponse<UserResponse> getUser(
            @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
            @PathVariable String id
    ) {

      authService.isTokenValid(mandatoryRequest.getAccessToken());

      User user = userService.findOne(id);

      return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
              null, toUserResponse(user));
    }

    @GetMapping(ApiPath.LOGOUT + ApiPath.ID)
    public BaseResponse<String> logout(
            @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
            @PathVariable String id
    ) {

      authService.isTokenValid(mandatoryRequest.getAccessToken());

      this.cacheService.createCache(id, mandatoryRequest.getAccessToken(), 86400);

      return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
              null, "Logout Successful");
    }

    @GetMapping
    public BaseResponse<List<UserResponse>> findAll(
            @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest
    ) {

      authService.isTokenValid(mandatoryRequest.getAccessToken());

      List<User> userList = userService.findAll();

      return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
              null, toUserResponseList(userList));
    }

    @PutMapping(ApiPath.ID)
    public BaseResponse<UserResponse> update(
            @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
            @ApiParam(value = "macAddress, sosNumber, emergencyNumber") @RequestBody UserRequest userRequest,
            @PathVariable String id
    ) {

      authService.isTokenValid(mandatoryRequest.getAccessToken());

      User user = userService.update(id, toUser(userRequest));

      return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
              null, toUserResponse(user));
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

    private UserResponse toUserResponse(User user, String token) {
      if(user == null) {
        return null;
      }

      UserResponse userResponse = new UserResponse();
      userResponse.setEmail(user.getEmail());
      userResponse.setName(user.getName());
      userResponse.setRole(user.getRole());
      userResponse.setPassword(user.getPassword());
      userResponse.setToken(token);
      userResponse.setId(user.getId());
      userResponse.setMacAddress(user.getMacAddress());
      userResponse.setSosNumber(user.getSosNumber());
      userResponse.setEmergencyNumber(user.getEmergencyNumber());

      return userResponse;
    }

    private List<UserResponse> toUserResponseList(List<User> userList) {
      List<UserResponse> userResponseList = new ArrayList<>();

      for(User user : userList) {
        userResponseList.add(toUserResponse(user));
      }

      return userResponseList;
    }

    @ModelAttribute
    public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
      return (MandatoryRequest) request.getAttribute("mandatory");
    }
}
