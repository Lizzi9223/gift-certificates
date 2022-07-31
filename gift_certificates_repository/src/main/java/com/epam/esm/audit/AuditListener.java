package com.epam.esm.audit;

import com.epam.esm.entity.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * listener for audit <br>
 * invoked on persist, merge or remove actions happening in repositories
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class AuditListener {

  @PrePersist
  public void onPrePersist(BaseEntity baseEntity) {
    audit("INSERT", baseEntity);
  }

  @PreUpdate
  public void onPreUpdate(BaseEntity baseEntity) {
    audit("UPDATE", baseEntity);
  }

  @PreRemove
  public void onPreRemove(BaseEntity baseEntity) {
    audit("DELETE", baseEntity);
  }

  private void audit(String operation, BaseEntity baseEntity) {
    baseEntity.setOperation(operation);
    baseEntity.setLocalDateTime(LocalDateTime.now());
  }
}
