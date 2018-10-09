package com.enrico.dg.home.security.service.api;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageService {

  Map<String, String> uploadImage(MultipartFile aFile, String id);
  String getImage(String id);
  void deleteImage(String id);
}
