package com.enrico.dg.home.security.entity.dao.common;

import com.enrico.dg.home.security.entity.constant.CollectionName;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@GeneratePojoBuilder
@Document(collection = CollectionName.SECURITY_SYSTEM)
public class SecuritySystemStatus extends BaseMongo {

  private Boolean isActive;

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  @Override
  public String toString() {
    return "SecuritySystemStatus{" +
            "isActive=" + isActive +
            '}';
  }
}
