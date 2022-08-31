package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * Order entity
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Entity(name = "orders")
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "total_cost")
  private BigDecimal price;

  @Column(name = "purchase_date")
  private LocalDateTime purchaseDate;

  @ManyToOne
  @JoinColumn(name = "users_id")
  private User user;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "orders_has_gift_certificate",
      joinColumns = @JoinColumn(name = "orders_id"),
      inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
  private Set<Certificate> certificates = new HashSet<>();

  public Order() {}

  public Order(
      Long id,
      BigDecimal price,
      LocalDateTime purchaseDate,
      User user,
      Set<Certificate> certificates) {
    this.id = id;
    this.price = price;
    this.purchaseDate = purchaseDate;
    this.user = user;
    this.certificates = certificates;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Set<Certificate> getCertificates() {
    return certificates;
  }

  public void setCertificates(Set<Certificate> certificates) {
    this.certificates = certificates;
  }
}
