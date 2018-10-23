package com.enrico.dg.home.security.service.api;

import com.enrico.dg.home.security.entity.dao.common.SecuritySystemStatus;

public interface SecuritySystemService {
  SecuritySystemStatus findSystemStatus();
  SecuritySystemStatus updateSystemStatus(SecuritySystemStatus securitySystemStatus);
  SecuritySystemStatus initializeSystemStatus(SecuritySystemStatus securitySystemStatus);
}
