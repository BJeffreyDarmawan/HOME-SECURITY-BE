package com.enrico.dg.home.security.entity.constant;

public interface ApiPath {

  String BASE_PATH = "/home-security";
  String ID = "/{id}";
  String ADD_USER = "/add-user";
  String SIGN_IN = "/sign-in";
  String UPLOAD_IMAGE_CLOUDINARY = "/upload-image-cloudinary";
  String GET_IMAGE_CLOUDINARY = "/get-image-cloudinary";
  String DELETE_IMAGE_CLOUDINARY = "/delete-image-cloudinary";
}
