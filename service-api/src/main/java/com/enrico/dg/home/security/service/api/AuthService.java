package com.enrico.dg.home.security.service.api;

import com.enrico.dg.home.security.entity.JWTokenClaim;
import com.enrico.dg.home.security.entity.dao.common.User;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {

  String createToken(String userId);

  JWTokenClaim getTokenInformation (String token);

  Boolean isTokenValid(String token);

  User register(User user, MultipartFile aFile);

  User findOne(String email, String password);
}