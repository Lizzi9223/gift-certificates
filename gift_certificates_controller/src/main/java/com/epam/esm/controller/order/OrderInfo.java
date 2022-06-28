package com.epam.esm.controller.order;

import com.epam.esm.dto.OrderDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Contains 2 field from OrderDto for order's controller
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class OrderInfo {
  private BigDecimal price;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private LocalDateTime purchaseDate;

  public OrderInfo(OrderDto orderDto) {
    this.price = orderDto.getPrice();
    this.purchaseDate = orderDto.getPurchaseDate();
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
    OrderInfo orderInfo = (OrderInfo) o;
    return Objects.equals(price, orderInfo.price)
        && Objects.equals(purchaseDate, orderInfo.purchaseDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price, purchaseDate);
  }

  @Override
  public String toString() {
    return "OrderInfo{" + "price=" + price + ", purchaseDate=" + purchaseDate + '}';
  }
}
