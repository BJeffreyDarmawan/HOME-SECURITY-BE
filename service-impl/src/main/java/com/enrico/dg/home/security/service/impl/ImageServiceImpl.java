package com.enrico.dg.home.security.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.libraries.exception.BusinessLogicException;
import com.enrico.dg.home.security.service.api.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

  @Override
  public void uploadImage(MultipartFile aFile) {

    Cloudinary cloudinary = cloudinaryConnect();

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    Date date = new Date();

    try {
      File file = Files.createTempFile("", aFile.getOriginalFilename()).toFile();
      aFile.transferTo(file);
      Map params = ObjectUtils.asMap(
              "public_id", file.getName(),
              "folder", formatter.format(date));
      Map upload = cloudinary.uploader().upload(file, params);
    } catch (IOException e) {
      LOGGER.info(e.getMessage());
      throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
              ResponseCode.SYSTEM_ERROR.getMessage());
    }
  }

  public Cloudinary cloudinaryConnect() {
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "xbcx",
            "api_key", "535677866642443",
            "api_secret", "GU_fJ4k09xeYQR87neYNc1GL-bo"));

    return cloudinary;
  }
}
