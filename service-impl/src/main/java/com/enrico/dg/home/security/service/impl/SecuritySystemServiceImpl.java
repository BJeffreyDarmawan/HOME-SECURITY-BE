package com.enrico.dg.home.security.service.impl;

import com.enrico.dg.home.security.dao.api.SecuritySystemRepository;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.SecuritySystem;
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
  public SecuritySystem findSystemStatus() {

    SecuritySystem securitySystem = securitySystemRepository.findByIsDeleted(0);

    if (securitySystem == null) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
              ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    return securitySystem;
  }

  @Override
  public SecuritySystem updateSystemStatus(SecuritySystem securitySystem) {
    SecuritySystem newSecuritySystem = securitySystemRepository.findByIsDeleted(0);

    if (securitySystem == null) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
              ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    newSecuritySystem.setActive(securitySystem.getActive());

    return securitySystemRepository.save(newSecuritySystem);
  }

  @Override
  public SecuritySystem initializeSystemStatus(SecuritySystem securitySystem) {

    SecuritySystem newSecuritySystem = new SecuritySystem();

    newSecuritySystem.setActive(securitySystem.getActive());

    return securitySystemRepository.save(newSecuritySystem);
  }
}
