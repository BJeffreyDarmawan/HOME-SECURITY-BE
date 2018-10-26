package com.enrico.dg.home.security.dao.api;

import com.enrico.dg.home.security.entity.dao.common.CloudinaryImage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ImageRepository extends MongoRepository<CloudinaryImage, String> {

  List<CloudinaryImage> findAllByCreatedDateAfterAndSensorsFeedbackMessageTypeContainsOrderByCreatedDateDesc(Date date, String msgType);
  List<CloudinaryImage> findAllByCreatedDateAfter(Date date);
  CloudinaryImage findByIsDeletedAndId(Integer isDeleted, String id);
}
