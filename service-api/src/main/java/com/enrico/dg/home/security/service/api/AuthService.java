package com.enrico.dg.home.security.service.api;

import com.enrico.dg.home.security.entity.JWTokenClaim;
import com.enrico.dg.home.security.entity.dao.common.User;

public interface AuthService {

  String createToken(String userId);

  JWTokenClaim getTokenInformation (String token);

  Boolean isTokenValid(String token);

  User register(User user);

  User findOne(String email, String password);
}