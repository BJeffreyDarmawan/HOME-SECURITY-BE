package com.enrico.dg.home.security.rest.web.controller;

import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.libraries.exception.BusinessLogicException;
import com.enrico.dg.home.security.libraries.exception.InvalidPasswordException;
import com.enrico.dg.home.security.libraries.utility.BaseResponseHelper;
import com.enrico.dg.home.security.rest.web.model.response.BaseResponse;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestControllerAdvice
public class ErrorHandlerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlerController.class);

  @ExceptionHandler(BindException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public BaseResponse bindException(BindException be) {
    LOGGER.info("BindException = {}", be);
    List<FieldError> bindErrors = be.getFieldErrors();
    List<String> errors = new ArrayList<>();
    for (FieldError fieldError : bindErrors) {
      errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
    }

    return BaseResponseHelper.constructResponse(ResponseCode.BIND_ERROR.getCode(),
        ResponseCode.BIND_ERROR.getMessage(),
        errors, null);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse exception(Exception e) {
    LOGGER.warn("Exception = {}", e);
    return BaseResponseHelper.constructResponse(ResponseCode.SYSTEM_ERROR.getCode(),
        ResponseCode.SYSTEM_ERROR.getMessage(),
        null, null);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseResponse runTimeException(RuntimeException re) {
    LOGGER.info("Runtime Error = {}", re);
    return BaseResponseHelper.constructResponse(ResponseCode.RUNTIME_ERROR.getCode(),
        ResponseCode.RUNTIME_ERROR.getMessage(),
        null, null);
  }

  @ExceptionHandler(BusinessLogicException.class)
  public BaseResponse businessLogicException(BusinessLogicException ble) {
    LOGGER.info("BusinessLogicException = {}", ble);
    return BaseResponseHelper.constructResponse(ble.getCode(), ble.getMessage(), null, null);
  }

  @ExceptionHandler(InvalidPasswordException.class)
  public BaseResponse invalidPasswordException(InvalidPasswordException ipe) {
    LOGGER.info("InvalidPasswordException = {}", ipe);
    return BaseResponseHelper.constructResponse(ipe.getCode(), ipe.getMessage(), ipe.getErrors(), null);
  }
}