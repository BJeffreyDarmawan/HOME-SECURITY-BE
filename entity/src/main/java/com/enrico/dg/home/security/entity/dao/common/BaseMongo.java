package com.enrico.dg.home.security.entity.dao.common;

import com.enrico.dg.home.security.entity.constant.fields.BaseMongoFields;
import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;

public abstract class BaseMongo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Field(value = BaseMongoFields.ID)
  private String id;

  @Version
  @Field(value = BaseMongoFields.VERSION)
  private Long version;

  @CreatedDate
  @Field(value = BaseMongoFields.CREATED_DATE)
  private Date createdDate;

  @CreatedBy
  @Field(value = BaseMongoFields.CREATED_BY)
  private String createdBy;

  @LastModifiedDate
  @Field(value = BaseMongoFields.UPDATED_DATE)
  private Date updatedDate;

  @LastModifiedBy
  @Field(value = BaseMongoFields.UPDATED_BY)
  private String updatedBy;

  @Field(value = BaseMongoFields.STORE_ID)
  private String storeId;

  @Field(value = BaseMongoFields.IS_DELETED)
  private Integer isDeleted = 0;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getStoreId() {
    return storeId;
  }

  public void setStoreId(String storeId) {
    this.storeId = storeId;
  }

  public Integer getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(Integer isDeleted) {
    this.isDeleted = isDeleted;
  }

  @Override
  public String toString() {
    return "BaseMongo{" +
        "id='" + id + '\'' +
        ", version=" + version +
        ", createdDate=" + createdDate +
        ", createdBy='" + createdBy + '\'' +
        ", updatedDate=" + updatedDate +
        ", updatedBy='" + updatedBy + '\'' +
        ", storeId='" + storeId + '\'' +
        ", isDeleted=" + isDeleted +
        "} " + super.toString();
  }
}
