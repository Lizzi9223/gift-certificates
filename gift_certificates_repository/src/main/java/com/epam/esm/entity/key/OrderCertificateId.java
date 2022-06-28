package com.epam.esm.entity.key;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderCertificateId implements Serializable {

  @Column(name = "orders_id")
  private int orderId;

  @Column(name = "gift_certificate_id")
  private int certificateId;

  public OrderCertificateId() {}

  public OrderCertificateId(int orderId, int certificateId) {
    this.orderId = orderId;
    this.certificateId = certificateId;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public int getCertificateId() {
    return certificateId;
  }

  public void setCertificateId(int certificateId) {
    this.certificateId = certificateId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderCertificateId that = (OrderCertificateId) o;
    return orderId == that.orderId && certificateId == that.certificateId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, certificateId);
  }

  @Override
  public String toString() {
    return "OrderCertificateId{" + "orderId=" + orderId + ", certificateId=" + certificateId + '}';
  }
}
