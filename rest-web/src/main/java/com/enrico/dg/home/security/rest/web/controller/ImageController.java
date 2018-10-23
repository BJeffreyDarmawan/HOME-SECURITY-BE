package com.enrico.dg.home.security.rest.web.controller;

import com.cloudinary.utils.ObjectUtils;
import com.enrico.dg.home.security.entity.constant.ApiPath;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.CloudinaryImage;
import com.enrico.dg.home.security.libraries.utility.BaseResponseHelper;
import com.enrico.dg.home.security.rest.web.model.request.MandatoryRequest;
import com.enrico.dg.home.security.rest.web.model.request.UnlockDoorRequest;
import com.enrico.dg.home.security.rest.web.model.response.BaseResponse;
import com.enrico.dg.home.security.rest.web.model.response.UnlockDoorResponse;
import com.enrico.dg.home.security.service.api.AuthService;
import com.enrico.dg.home.security.service.api.ImageService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ApiPath.BASE_PATH + ApiPath.IMAGE)
public class ImageController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

  @Autowired
  private AuthService authService;

  @Autowired
  private ImageService imageService;

  @GetMapping
  private BaseResponse<List<CloudinaryImage>> getImage(
          @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
          @ApiParam(value = "yyyy/MM") @RequestParam @DateTimeFormat(pattern="yyyy/MM") Date date
  ) {

    authService.isTokenValid(mandatoryRequest.getAccessToken());

    List<CloudinaryImage> cloudinaryImageList = imageService.getImages(date);

    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, cloudinaryImageList);
  }

//  @DeleteMapping(ApiPath.DELETE_IMAGE_CLOUDINARY + ApiPath.ID)
//  private BaseResponse<String> deleteImage(
//          @ApiIgnore @Valid @ModelAttribute MandatoryRequest mandatoryRequest,
//          @PathVariable String id
//  ) {
//
//    authService.isTokenValid(mandatoryRequest.getAccessToken());
//
//    imageService.deleteImage(id);
//
//    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
//            null, "Successfully Delete Image");
//  }

  @PostMapping(ApiPath.HARDWARE)
  public BaseResponse<Map<String, String>> uploadCapturedImageToCloudinary(
          @RequestParam(value = "capturedImage") MultipartFile aFile
  ) {

    Map<String, String> uploadResult = imageService.uploadCapturedImage(aFile);

    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, uploadResult);
  }

  @PostMapping(ApiPath.USER_IMAGE + ApiPath.ID)
  public BaseResponse<Map<String, String>> uploadSelfieImageToCloudinary(
          @RequestParam(value = "selfieImage") MultipartFile aFile,
          @PathVariable String id
  ) {

    Map<String, String> uploadResult = imageService.uploadSelfieImage(aFile, id);

    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, uploadResult);
  }

  @PutMapping(ApiPath.MESSAGE + ApiPath.ID)
  public BaseResponse<CloudinaryImage> updateCapturedImageMessage(
          @RequestBody UnlockDoorRequest unlockDoorRequest,
          @PathVariable String id
  ) {

    CloudinaryImage cloudinaryImage = imageService.updateImageMessage(unlockDoorRequest, id);

    return BaseResponseHelper.constructResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
            null, cloudinaryImage);
  }

//  private UnlockDoorResponse toUnlockDoorResponse(UnlockDoorRequest unlockDoorRequest) {
//
//    Map unlockDoorMessageMap = ObjectUtils.asMap(
//            "messageType", unlockDoorRequest.getMessageType(),
//            "message", unlockDoorRequest.getMessage(),
//            "command", unlockDoorRequest.getCommand());
//
//    UnlockDoorResponse unlockDoorResponse = new UnlockDoorResponse();
//    unlockDoorResponse.setSensorsFeedback(unlockDoorMessageMap);
//
//    return unlockDoorResponse;
//  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }
}