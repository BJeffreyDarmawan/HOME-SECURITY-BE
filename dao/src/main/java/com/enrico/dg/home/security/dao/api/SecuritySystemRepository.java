package com.enrico.dg.home.security.dao.api;

import com.enrico.dg.home.security.entity.dao.common.SecuritySystem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SecuritySystemRepository extends MongoRepository<SecuritySystem, String> {

  SecuritySystem findByIsDeleted(Integer isDeleted);
}
