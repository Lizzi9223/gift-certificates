package com.epam.esm.entity;

import com.epam.esm.entity.key.OrderCertificateId;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * OrderCertificate entity
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Entity(name = "orders_has_gift_certificate")
public class OrderCertificate extends BaseEntity{

  @EmbeddedId private OrderCertificateId orderCertificateId;

  public OrderCertificate() {}

  public OrderCertificate(OrderCertificateId orderCertificateId) {
    this.orderCertificateId = orderCertificateId;
  }

  public OrderCertificateId getOrderCertificateId() {
    return orderCertificateId;
  }

  public void setOrderCertificateId(OrderCertificateId orderCertificateId) {
    this.orderCertificateId = orderCertificateId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderCertificate that = (OrderCertificate) o;
    return Objects.equals(orderCertificateId, that.orderCertificateId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderCertificateId);
  }

  @Override
  public String toString() {
    return "OrderCertificate{" + "orderCertificateId=" + orderCertificateId + '}';
  }
}
