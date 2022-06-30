package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Order entity
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Entity(name = "orders")
public class Order extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "users_id")
  private int userId;

  @Column(name = "total_cost")
  private BigDecimal price;

  @Column(name = "purchase_date")
  private LocalDateTime purchaseDate;

  public Order() {}

  public Order(int id, int userId, BigDecimal price, LocalDateTime purchaseDate) {
    this.id = id;
    this.userId = userId;
    this.price = price;
    this.purchaseDate = purchaseDate;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public LocalDateTime getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(LocalDateTime purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return id == order.id
        && userId == order.userId
        && Objects.equals(price, order.price)
        && Objects.equals(purchaseDate, order.purchaseDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, price, purchaseDate);
  }

  @Override
  public String toString() {
    return "Order{"
        + "id="
        + id
        + ", userId="
        + userId
        + ", price="
        + price
        + ", purchaseDate="
        + purchaseDate
        + '}';
  }
}
