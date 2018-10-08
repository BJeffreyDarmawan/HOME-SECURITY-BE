package com.enrico.dg.home.security.service.impl;

import com.enrico.dg.home.security.dao.api.UserRepository;
import com.enrico.dg.home.security.entity.JWTokenClaimBuilder;
import com.enrico.dg.home.security.entity.dao.common.User;
import com.enrico.dg.home.security.libraries.utility.PasswordHelper;
import com.enrico.dg.home.security.service.api.AuthService;
import com.enrico.dg.home.security.entity.JWTokenClaim;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.libraries.exception.BusinessLogicException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

import com.enrico.dg.home.security.service.api.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AuthServiceImpl implements AuthService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

  @Autowired
  private ImageService imageService;

  @Autowired
  private UserRepository userRepository;

  @Value("${home.security.auth.secret}")
  private String TOKEN_SECRET;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public String createToken(String userId) {
    try {
      Date currentDate = new Date();
      Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
      return JWT.create()
          .withClaim("userId", userId)
          .withClaim("createdAt", currentDate)
          .withExpiresAt(new Date(currentDate.getTime() + 3600000))
          .sign(algorithm);
    } catch (Exception exception) {
      LOGGER.error(exception.getMessage());
      throw new BusinessLogicException(ResponseCode.RUNTIME_ERROR.getCode(),
          ResponseCode.RUNTIME_ERROR.getMessage());
    }
  }

  @Override
  public JWTokenClaim getTokenInformation(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
      JWTVerifier verifier = JWT.require(algorithm)
          .build();
      DecodedJWT jwt = verifier.verify(token);
      return new JWTokenClaimBuilder()
          .withUserId(jwt.getClaim("userId").asString())
          .build();

    } catch (Exception exception) {
      LOGGER.error(exception.getMessage());
      throw new BusinessLogicException(ResponseCode.INVALID_TOKEN.getCode(),
          ResponseCode.INVALID_TOKEN.getMessage());
    }
  }

  @Override
  public Boolean isTokenValid(String token) {
    JWTokenClaim jwTokenClaim = this.getTokenInformation(token);
    return jwTokenClaim != null;
  }

  @Override
  public User register(User user, MultipartFile aFile) {

    User newUser = new User();

    if(PasswordHelper.isPasswordValid(user.getPassword())) {
        newUser.setPassword(PasswordHelper.encryptPassword(user.getPassword()));
    }

    String imageUrl = imageService.uploadImage(aFile);

    newUser.setName(user.getName());
    newUser.setEmail(user.getEmail());
    newUser.setRole(user.getRole());
    newUser.setImageUrl(imageUrl);

    try{
      return userRepository.save(newUser);
    } catch (Exception e) {
      throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
              ResponseCode.DUPLICATE_DATA.getMessage());
    }
  }

  @Override
  public User findOne(String email, String password) {

    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
              ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    return user;
  }
}