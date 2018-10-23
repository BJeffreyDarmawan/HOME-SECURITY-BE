package com.enrico.dg.home.security.dao.api;

import com.enrico.dg.home.security.entity.dao.common.SecuritySystemStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SecuritySystemRepository extends MongoRepository<SecuritySystemStatus, String> {

  SecuritySystemStatus findByIsDeleted(Integer isDeleted);
}
