package com.enrico.dg.home.security.service.api;

import com.enrico.dg.home.security.entity.dao.common.CloudinaryImage;
import com.enrico.dg.home.security.rest.web.model.request.UnlockDoorRequest;
import com.enrico.dg.home.security.rest.web.model.response.UnlockDoorResponse;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ImageService {

  Map<String, String> uploadCapturedImage(MultipartFile aFile);
  Map<String, String> uploadSelfieImage(MultipartFile aFile, String id);
  List<CloudinaryImage> getImages(Date date);
  CloudinaryImage updateImageMessage(UnlockDoorRequest unlockDoorRequest, String id);
//  void deleteImage(String id);
}
