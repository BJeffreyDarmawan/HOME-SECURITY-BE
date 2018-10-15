package com.enrico.dg.home.security.entity.dao.common;

import com.enrico.dg.home.security.entity.constant.CollectionName;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;


@GeneratePojoBuilder
@Document(collection = CollectionName.CAPTURED_IMAGES)
public class CloudinaryImage extends BaseMongo {

  private String imageUrl;
  private String publicId;

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

  @Override
  public String toString() {
    return "CloudinaryImage{" +
            "imageUrl='" + imageUrl + '\'' +
            ", publicId='" + publicId + '\'' +
            '}';
  }
}
