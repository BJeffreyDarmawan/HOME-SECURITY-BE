package com.enrico.dg.home.security.service.api;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  void uploadImage(MultipartFile aFile);
}
