package com.enrico.dg.home.security.rest.web.controller;

import com.enrico.dg.home.security.entity.constant.ApiPath;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.User;
import com.enrico.dg.home.security.libraries.exception.BusinessLogicException;
import com.enrico.dg.home.security.libraries.utility.BaseResponseHelper;
import com.enrico.dg.home.security.libraries.utility.PasswordHelper;
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

    @PostMapping(ApiPath.ADD_USER)
    public BaseResponse<UserResponse> addUser(
            @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
            @RequestBody UserRequest userRequest) {

        if(authService.isTokenValid(mandatoryRequest.getAccessToken())) {

          User user = authService.register(toUser(userRequest));

          return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                  null, toUserResponse(user));
        } else {
          throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
                  ResponseCode.INVALID_TOKEN.getMessage());
        }
    }

    @PostMapping(ApiPath.SIGN_IN)
    public BaseResponse<UserResponse> signIn(@RequestParam String email, @RequestParam String password) {

      User user = authService.findOne(email, password);

      if(PasswordHelper.matchPassword(password, user.getPassword())) {

        String token = authService.createToken(user.getId());

        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, toUserResponse(user, token));
      } else {
        throw new BusinessLogicException(ResponseCode.INVALID_PASSWORD.getCode(),
                ResponseCode.INVALID_PASSWORD.getMessage());
      }
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

      return userResponse;
    }

    @ModelAttribute
    public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
      return (MandatoryRequest) request.getAttribute("mandatory");
    }
}
