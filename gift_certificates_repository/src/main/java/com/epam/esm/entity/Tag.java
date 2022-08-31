package com.epam.esm.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * Tag entity
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Entity(name = "tag")
public class Tag extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "tags")
  private Set<Certificate> certificates = new HashSet<>();

  public Tag() {}

  public Tag(String name) {
    this.name = name;
  }

  public Tag(Long id, String name, Set<Certificate> posts) {
    this.id = id;
    this.name = name;
    this.certificates = posts;
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

  public Set<Certificate> getCertificates() {
    return certificates;
  }

  public void setCertificates(Set<Certificate> certificates) {
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
    Tag tag = (Tag) o;
    return Objects.equals(name, tag.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
