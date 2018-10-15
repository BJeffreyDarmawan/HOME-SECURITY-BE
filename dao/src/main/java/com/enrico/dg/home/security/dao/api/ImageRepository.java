package com.enrico.dg.home.security.dao.api;

import com.enrico.dg.home.security.entity.dao.common.CloudinaryImage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<CloudinaryImage, String> {

  CloudinaryImage findByIsDeletedAndId(Integer isDeleted, String id);
}
