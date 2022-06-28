package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * Order DTO
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class OrderDto {
  private int id;
  @NotNull private int userId;
  private BigDecimal price;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private LocalDateTime purchaseDate;

  @NotNull
  private List<CertificateDto> certificates;

  public OrderDto() {}

  public OrderDto(
      int id,
      int userId,
      BigDecimal price,
      LocalDateTime purchaseDate,
      List<CertificateDto> certificates) {
    this.id = id;
    this.userId = userId;
    this.price = price;
    this.purchaseDate = purchaseDate;
    this.certificates = certificates;
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

  public List<CertificateDto> getCertificates() {
    return certificates;
  }

  public void setCertificates(List<CertificateDto> certificates) {
    this.certificates = certificates;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderDto orderDto = (OrderDto) o;
    return id == orderDto.id
        && userId == orderDto.userId
        && Objects.equals(price, orderDto.price)
        && Objects.equals(purchaseDate, orderDto.purchaseDate)
        && Objects.equals(certificates, orderDto.certificates);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, price, purchaseDate, certificates);
  }

  @Override
  public String toString() {
    return "OrderDto{"
        + "id="
        + id
        + ", userId="
        + userId
        + ", price="
        + price
        + ", purchaseDate="
        + purchaseDate
        + ", certificates="
        + certificates
        + '}';
  }
}
