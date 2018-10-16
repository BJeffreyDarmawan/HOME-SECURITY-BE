package com.enrico.dg.home.security.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.enrico.dg.home.security.dao.api.ImageRepository;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.CloudinaryImage;
import com.enrico.dg.home.security.entity.dao.common.CloudinaryImageBuilder;
import com.enrico.dg.home.security.libraries.exception.BusinessLogicException;
import com.enrico.dg.home.security.service.api.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

  @Value("${home.security.cloudinary.url}")
  private String CLOUDINARY_URL;

  @Autowired
  private ImageRepository imageRepository;

  @Override
  public Map<String, String> uploadImage(MultipartFile aFile) {

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

      CloudinaryImage cloudinaryImage = new CloudinaryImageBuilder()
              .withImageUrl((String) upload.get("url"))
              .withPublicId((String) upload.get("public_id"))
              .build();

      imageRepository.save(cloudinaryImage);

      return upload;
    } catch (IOException e) {
      LOGGER.info(e.getMessage());
      throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
              ResponseCode.SYSTEM_ERROR.getMessage());
    }
  }

  @Override
  public List<String> getImages(Date date) {

    try{
      List<CloudinaryImage> cloudinaryImages = imageRepository.findAllByCreatedDateAfter(date);
      List<String> imageUrls = new ArrayList<>();

      for(CloudinaryImage cloudinaryImage : cloudinaryImages) {
        imageUrls.add(cloudinaryImage.getImageUrl());
      }

      return imageUrls;
    } catch (Exception e) {
      throw new BusinessLogicException(ResponseCode.RUNTIME_ERROR.getCode(),
              ResponseCode.RUNTIME_ERROR.getMessage());
    }
  }

//  @Override
//  public void deleteImage(String id) {
//
//    Cloudinary cloudinary = cloudinaryConnect();
//
//    try {
//      CloudinaryImage cloudinaryImage = imageRepository.findByIsDeletedAndId(0, id);
//
//      cloudinary.uploader().destroy(cloudinaryImage.getPublicId(), ObjectUtils.emptyMap());
//
//      imageRepository.delete(cloudinaryImage);
//    } catch (IOException e) {
//      LOGGER.info(e.getMessage());
//      throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
//              ResponseCode.SYSTEM_ERROR.getMessage());
//    }
//  }

  public Cloudinary cloudinaryConnect() {
//    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//            "cloud_name", "xbcx",
//            "api_key", "535677866642443",
//            "api_secret", "GU_fJ4k09xeYQR87neYNc1GL-bo"));

    Cloudinary cloudinary = new Cloudinary(CLOUDINARY_URL);

    return cloudinary;
  }
}
