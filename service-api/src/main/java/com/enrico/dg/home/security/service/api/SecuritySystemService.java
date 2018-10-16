package com.enrico.dg.home.security.service.api;

import com.enrico.dg.home.security.entity.dao.common.SecuritySystem;

public interface SecuritySystemService {
  SecuritySystem findSystemStatus();
  SecuritySystem updateSystemStatus(SecuritySystem securitySystem);
  SecuritySystem initializeSystemStatus(SecuritySystem securitySystem);
}
