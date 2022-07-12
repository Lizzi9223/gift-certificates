package com.epam.esm.entity;

import com.epam.esm.audit.AuditListener;
import com.epam.esm.consts.NamedQueriesKeys;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NaturalId;

/**
 * Certificate entity
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Entity(name = "gift_certificate")
@EntityListeners(AuditListener.class)
@NamedQuery(
    name = NamedQueriesKeys.CERTIFICATE_FIND_BY_NAME,
    query = "select g from gift_certificate g where g.name = :name")
public class Certificate extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  private BigDecimal price;

  private int duration;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  @Column(name = "last_update_date")
  private LocalDateTime lastUpdateDate;

  @ManyToMany(mappedBy = "certificates")
  private Set<Order> orders = new HashSet<>();

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(
      name = "gift_certificate_has_tag",
      joinColumns = @JoinColumn(name = "gift_certificate_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags = new HashSet<>();

  public Certificate() {}

  public Certificate(
      Long id,
      String name,
      String description,
      BigDecimal price,
      int duration,
      LocalDateTime createDate,
      LocalDateTime lastUpdateDate,
      Set<Order> orders,
      Set<Tag> tags) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.duration = duration;
    this.createDate = createDate;
    this.lastUpdateDate = lastUpdateDate;
    this.orders = orders;
    this.tags = tags;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public LocalDateTime getLastUpdateDate() {
    return lastUpdateDate;
  }

  public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }

  public Set<Order> getOrders() {
    return orders;
  }

  public void setOrders(Set<Order> orders) {
    this.orders = orders;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  public void addTag(Tag tag) {
    tags.add(tag);
    tag.getCertificates().add(this);
  }

  public void removeTag(Tag tag) {
    tags.remove(tag);
    tag.getCertificates().remove(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Certificate certificate = (Certificate) o;
    return Objects.equals(name, certificate.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Certificate{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", price="
        + price
        + ", duration="
        + duration
        + ", createDate="
        + createDate
        + ", lastUpdateDate="
        + lastUpdateDate
        + ", orders="
        + orders
        + ", tags="
        + tags
        + '}';
  }
}
