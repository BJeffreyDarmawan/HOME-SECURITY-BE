package com.enrico.dg.home.security.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.enrico.dg.home.security.dao.api.ImageRepository;
import com.enrico.dg.home.security.dao.api.UserRepository;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.*;
import com.enrico.dg.home.security.libraries.exception.BusinessLogicException;
import com.enrico.dg.home.security.rest.web.model.request.UnlockDoorRequest;
import com.enrico.dg.home.security.service.api.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceImpl.class);

  @Value("${home.security.cloudinary.url}")
  private String CLOUDINARY_URL;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EmailServiceImpl emailService;

  @Override
  public Map<String, String> uploadCapturedImage(MultipartFile aFile) {

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
              .withRead(Boolean.FALSE)
              .build();

      imageRepository.save(cloudinaryImage);

      upload.put("notification", "There are some activities at your house. Please check the attached picture.");
      upload.put("id", cloudinaryImage.getId());

      return upload;
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
      throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
              ResponseCode.SYSTEM_ERROR.getMessage());
    }
  }

  @Override
  public Map<String, String> uploadSelfieImage(MultipartFile aFile, String id) {

    Cloudinary cloudinary = cloudinaryConnect();

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    Date date = new Date();

    try {
      User user = userRepository.findByIsDeletedAndId(0, id);

      File file = Files.createTempFile("", aFile.getOriginalFilename()).toFile();
      aFile.transferTo(file);
      Map params = ObjectUtils.asMap(
              "public_id", file.getName(),
              "folder", formatter.format(date));
      Map upload = cloudinary.uploader().upload(file, params);

      user.setImageUrl((String) upload.get("url"));
      user.setPublicId((String) upload.get("public_id"));
      userRepository.save(user);

      return upload;
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
      throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
              ResponseCode.SYSTEM_ERROR.getMessage());
    }
  }

  @Override
  public CloudinaryImage getImageById(String id) {

    CloudinaryImage cloudinaryImage = imageRepository.findByIsDeletedAndId(0, id);

    if(cloudinaryImage == null) {
      return null;
    }

    return cloudinaryImage;
  }


  @Override
  public List<CloudinaryImage> getImages(Date date) {

    try{
      List<CloudinaryImage> cloudinaryImages = imageRepository.findAllByCreatedDateAfterOrderByCreatedDateDesc(date);

      return cloudinaryImages;
    } catch (Exception e) {
      throw new BusinessLogicException(ResponseCode.RUNTIME_ERROR.getCode(),
              ResponseCode.RUNTIME_ERROR.getMessage());
    }
  }

  @Override
  public List<CloudinaryImage> getWarningImages(Date date) {

    try{
      List<CloudinaryImage> cloudinaryImages = imageRepository
              .findAllByCreatedDateAfterAndSensorsFeedbackMessageTypeContainsOrderByCreatedDateDesc(date, "WARNING");

      return cloudinaryImages;
    } catch (Exception e) {
      throw new BusinessLogicException(ResponseCode.RUNTIME_ERROR.getCode(),
              ResponseCode.RUNTIME_ERROR.getMessage());
    }
  }

  @Override
  public CloudinaryImage updateImageMessage(UnlockDoorRequest unlockDoorRequest, String id) {

    CloudinaryImage cloudinaryImage = imageRepository.findByIsDeletedAndId(0, id);

    if(cloudinaryImage == null) {
      return null;
    }

    SensorsFeedbackMap sensorsFeedbackMap = new SensorsFeedbackMapBuilder()
            .withCommand(unlockDoorRequest.getCommand().toUpperCase())
            .withMessage(unlockDoorRequest.getMessage())
            .withMessageType(unlockDoorRequest.getMessageType().toUpperCase())
            .build();

    cloudinaryImage.setSensorsFeedback(sensorsFeedbackMap);

    sendMessage(sensorsFeedbackMap);

    imageRepository.save(cloudinaryImage);

    TimerTask task = new TimerTask() {
      @Override
      public void run() {

        CloudinaryImage updatedCloudinaryImage = imageRepository.findByIsDeletedAndId(0, id);

        if(updatedCloudinaryImage.getRead().equals(Boolean.FALSE) && sensorsFeedbackMap.getMessageType().equals("WARNING")) {
          LOGGER.info("Sending Email...");
          emailService.sendMail();
          LOGGER.info("Email Sent!");
        } else {
          LOGGER.info("Clear or Read by User Already");
        }
      }
    };
    Timer timer = new Timer("Timer");

    long delay = 120000L;
    timer.schedule(task, delay);

    return cloudinaryImage;
  }

  @Override
  public CloudinaryImage updateIsRead(String id) {

    CloudinaryImage cloudinaryImage = imageRepository.findByIsDeletedAndId(0, id);

    if(cloudinaryImage == null) {
      return null;
    }

    cloudinaryImage.setRead(Boolean.TRUE);

    return imageRepository.save(cloudinaryImage);
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

  private Cloudinary cloudinaryConnect() {
//    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//            "cloud_name", "xbcx",
//            "api_key", "535677866642443",
//            "api_secret", "GU_fJ4k09xeYQR87neYNc1GL-bo"));

    Cloudinary cloudinary = new Cloudinary(CLOUDINARY_URL);

    return cloudinary;
  }
  //this is supposed to be created in outboundserviceimpl, if have time just change it
  private void sendMessage(SensorsFeedbackMap sensorsFeedback) {

    final String unlockDoorUri = "http://socket-fp-soft-eng.herokuapp.com/command-unlock-door";
    final String windowNotificationUri = "http://socket-fp-soft-eng.herokuapp.com/message-notification";

    RestTemplate restTemplate = new RestTemplate();
    SensorsFeedbackMap result;

    if(sensorsFeedback.getCommand().equals("UNLOCK")) {
      result = restTemplate.postForObject(unlockDoorUri, sensorsFeedback, SensorsFeedbackMap.class);
    } else {
      //command is probably "Alert"
      result = restTemplate.postForObject(windowNotificationUri, sensorsFeedback, SensorsFeedbackMap.class);
    }

    LOGGER.info(result.toString());
  }
}
