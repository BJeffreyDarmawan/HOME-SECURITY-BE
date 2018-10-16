package com.enrico.dg.home.security.service.api;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ImageService {

  Map<String, String> uploadImage(MultipartFile aFile);
  List<String> getImages(Date date);
//  void deleteImage(String id);
}
