package com.enrico.dg.home.security.libraries.utility;

import com.enrico.dg.home.security.rest.web.model.response.BaseResponse;
import com.enrico.dg.home.security.rest.web.model.response.BaseResponseBuilder;
import java.util.Date;
import java.util.List;

public class BaseResponseHelper {

  private BaseResponseHelper(){}

  public static <T> BaseResponse<T> constructResponse(String code, String message, List<String> errors, T data) {
    return (new BaseResponseBuilder()).withCode(code).withMessage(message).withErrors(errors).withServerTime(new Date()).withData(data).build();
  }
}