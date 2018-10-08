package com.enrico.dg.home.security.rest.web.controller;

import com.enrico.dg.home.security.entity.constant.ApiPath;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.User;
import com.enrico.dg.home.security.libraries.exception.BusinessLogicException;
import com.enrico.dg.home.security.libraries.utility.BaseResponseHelper;
import com.enrico.dg.home.security.libraries.utility.PasswordHelper;
import com.enrico.dg.home.security.rest.web.model.request.MandatoryRequest;
import com.enrico.dg.home.security.rest.web.model.response.BaseResponse;
import com.enrico.dg.home.security.rest.web.model.response.UserResponse;
import com.enrico.dg.home.security.service.api.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
            @RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String role,
            @RequestParam(value = "uploadSelfie") MultipartFile aFile
    ) {

        authService.isTokenValid(mandatoryRequest.getAccessToken());

        User user = authService.register(toUser(name,email,password,role), aFile);

        return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                null, toUserResponse(user));
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

//    @PostMapping(ApiPath.UPLOAD_IMAGE_CLOUDINARY)
//    public BaseResponse<String> uploadImageToCloudinary(
//            @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
//            @RequestParam(value = "uploadSelfie") MultipartFile aFile
//    ) {
//
//      authService.isTokenValid(mandatoryRequest.getAccessToken());
//
//      imageService.uploadImage(aFile);
//
//      return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
//              null, "Successfully Upload Image");
//    }

    private User toUser(String name, String email, String password, String role) {
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);
        user.setRole(role);

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
