package com.enrico.dg.home.security.service.impl;

import com.enrico.dg.home.security.entity.JWTokenClaimBuilder;
import com.enrico.dg.home.security.service.api.AuthService;
import com.enrico.dg.home.security.entity.JWTokenClaim;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.libraries.exception.BusinessLogicException;
import com.enrico.dg.home.security.libraries.exception.InvalidPasswordException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.codec.digest.DigestUtils;
import org.passay.AlphabeticalSequenceRule;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.NumericalSequenceRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.QwertySequenceRule;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

  @Value("${home.security.auth.secret}")
  private String TOKEN_SECRET;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public String createToken(String userId, int userLevel, String currentAccessToken) {
    try {
      Date currentDate = new Date();
      Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
      return JWT.create()
          .withClaim("userId", userId)
          .withClaim("userLevel", userLevel)
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
          .withUserLevel(jwt.getClaim("userLevel").asInt())
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

  private boolean isPasswordValid(String password) {
    PasswordValidator validator = new PasswordValidator(Arrays.asList(
        new LengthRule(8, 30),
        new UppercaseCharacterRule(1),
        new DigitCharacterRule(1),
        new SpecialCharacterRule(1),
        new NumericalSequenceRule(3,false),
        new AlphabeticalSequenceRule(3,false),
        new QwertySequenceRule(3,false),
        new WhitespaceRule()));

    RuleResult result = validator.validate(new PasswordData(password));
    if (!result.isValid()) {
      Set<String> errorList = new HashSet<>();
      for (RuleResultDetail ruleResultDetail : result.getDetails()) {
        errorList.add(ruleResultDetail.getErrorCode());
      }
      throw new InvalidPasswordException(ResponseCode.INVALID_PASSWORD.getCode(), ResponseCode.INVALID_PASSWORD.getMessage(), new ArrayList<>(errorList));
    }
    return true;
  }
}

