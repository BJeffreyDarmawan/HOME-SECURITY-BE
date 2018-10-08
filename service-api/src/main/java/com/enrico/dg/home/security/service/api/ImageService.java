package com.enrico.dg.home.security.service.api;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  String uploadImage(MultipartFile aFile);
  String getImage(String id);
}
