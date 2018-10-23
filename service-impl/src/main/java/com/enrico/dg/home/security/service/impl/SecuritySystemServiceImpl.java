package com.enrico.dg.home.security.service.impl;

import com.enrico.dg.home.security.dao.api.SecuritySystemRepository;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.SecuritySystemStatus;
import com.enrico.dg.home.security.libraries.exception.BusinessLogicException;
import com.enrico.dg.home.security.service.api.SecuritySystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecuritySystemServiceImpl implements SecuritySystemService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SecuritySystemServiceImpl.class);

  @Autowired
  private SecuritySystemRepository securitySystemRepository;

  @Override
  public SecuritySystemStatus findSystemStatus() {

    SecuritySystemStatus securitySystemStatus = securitySystemRepository.findByIsDeleted(0);

    if (securitySystemStatus == null) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
              ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    return securitySystemStatus;
  }

  @Override
  public SecuritySystemStatus updateSystemStatus(SecuritySystemStatus securitySystemStatus) {
    SecuritySystemStatus newSecuritySystemStatus = securitySystemRepository.findByIsDeleted(0);

    if (securitySystemStatus == null) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
              ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    newSecuritySystemStatus.setActive(securitySystemStatus.getActive());

    return securitySystemRepository.save(newSecuritySystemStatus);
  }

  @Override
  public SecuritySystemStatus initializeSystemStatus(SecuritySystemStatus securitySystemStatus) {

    SecuritySystemStatus newSecuritySystemStatus = new SecuritySystemStatus();

    newSecuritySystemStatus.setActive(securitySystemStatus.getActive());

    return securitySystemRepository.save(newSecuritySystemStatus);
  }
}
