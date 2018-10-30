package com.enrico.dg.home.security.service.api;

import com.enrico.dg.home.security.entity.dao.common.CloudinaryImage;
import com.enrico.dg.home.security.rest.web.model.request.UnlockDoorRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ImageService {

  Map<String, String> uploadCapturedImage(MultipartFile aFile);
  Map<String, String> uploadSelfieImage(MultipartFile aFile, String id);
  CloudinaryImage getImageById(String id);
  List<CloudinaryImage> getImages(Date date);
  List<CloudinaryImage> getWarningImages(Date date);
  CloudinaryImage updateImageMessage(UnlockDoorRequest unlockDoorRequest, String id);
  CloudinaryImage updateIsRead(String id);
//  void deleteImage(String id);
}
