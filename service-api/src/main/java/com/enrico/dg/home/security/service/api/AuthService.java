package com.enrico.dg.home.security.service.api;

import com.enrico.dg.home.security.entity.JWTokenClaim;

public interface AuthService {

  String createToken(String userId, int userLevel, String currentAccessToken);

  JWTokenClaim getTokenInformation (String token);

  Boolean isTokenValid(String token);
}