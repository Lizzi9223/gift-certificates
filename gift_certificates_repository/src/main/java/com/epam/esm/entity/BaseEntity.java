package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * super class for all entities
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@EntityListeners(AuditListener.class)
@MappedSuperclass
public class BaseEntity {
  @Column(name = "operation")
  private String operation;

  @Column(name = "timestamp")
  private LocalDateTime localDateTime;

  public String getOperation() {
    return operation;
  }
  public void setOperation(String operation) {
    this.operation = operation;
  }

  public LocalDateTime getLocalDateTime() {
    return localDateTime;
  }

  public void setLocalDateTime(LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
  }
}
