package com.enrico.dg.home.security.rest.web.controller;

import com.enrico.dg.home.security.entity.constant.ApiPath;
import com.enrico.dg.home.security.rest.web.model.request.MandatoryRequest;
import com.enrico.dg.home.security.rest.web.model.response.BaseResponse;
import com.enrico.dg.home.security.service.api.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(ApiPath.BASE_PATH)
public class ImageController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

  @Autowired
  private ImageService imageService;

  @GetMapping(ApiPath.GET_IMAGE_CLOUDINARY)
  private BaseResponse<String> getImage() {

    return null;
  }

  @DeleteMapping(ApiPath.DELETE_IMAGE_CLOUDINARY)
  private BaseResponse<String> deleteImage() {

    return null;
  }

  @ModelAttribute
  public MandatoryRequest getMandatoryParameter(HttpServletRequest request) {
    return (MandatoryRequest) request.getAttribute("mandatory");
  }
}