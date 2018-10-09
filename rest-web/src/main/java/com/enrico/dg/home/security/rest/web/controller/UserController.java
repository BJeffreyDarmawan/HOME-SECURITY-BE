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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthService authService;

    @PostMapping(value = ApiPath.ADD_USER)
    public BaseResponse<UserResponse> addUser(
            @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
            @RequestBody UserRequest userRequest
    ) {

        authService.isTokenValid(mandatoryRequest.getAccessToken());

        User user = authService.register(toUser(userRequest));

        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, toUserResponse(user));
    }

    @PostMapping(ApiPath.SIGN_IN)
    public BaseResponse<UserResponse> signIn(@RequestBody LoginRequest loginRequest) {

      User user = authService.login(loginRequest.getEmail());

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
    public BaseResponse<UserResponse> getUser(@PathVariable String id) {

      User user = authService.findOne(id);

      return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
              null, toUserResponse(user));
    }

    private User toUser(UserRequest userRequest) {
        User user = new User();
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setRole(userRequest.getRole());

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
        userResponse.setImageUrl(user.getImageUrl());
        userResponse.setPublicId(user.getPublicId());
        userResponse.setId(user.getId());

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

      return userResponse;
    }

    @ModelAttribute
    public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
      return (MandatoryRequest) request.getAttribute("mandatory");
    }
}
