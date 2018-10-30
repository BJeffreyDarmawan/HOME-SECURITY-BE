package com.enrico.dg.home.security.entity.dao.common;

import com.enrico.dg.home.security.entity.constant.CollectionName;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;


@GeneratePojoBuilder
@Document(collection = CollectionName.CAPTURED_IMAGES)
public class CloudinaryImage extends BaseMongo {

  private String imageUrl;
  private String publicId;
  private SensorsFeedbackMap sensorsFeedback;
  private Boolean isRead;

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getPublicId() {
    return publicId;
  }

  public void setPublicId(String publicId) {
    this.publicId = publicId;
  }

  public SensorsFeedbackMap getSensorsFeedback() {
    return sensorsFeedback;
  }

  public void setSensorsFeedback(SensorsFeedbackMap sensorsFeedback) {
    this.sensorsFeedback = sensorsFeedback;
  }

  public Boolean getRead() {
    return isRead;
  }

  public void setRead(Boolean read) {
    isRead = read;
  }

  @Override
  public String toString() {
    return "CloudinaryImage{" +
            "imageUrl='" + imageUrl + '\'' +
            ", publicId='" + publicId + '\'' +
            ", sensorsFeedback=" + sensorsFeedback +
            ", isRead=" + isRead +
            '}';
  }
}
