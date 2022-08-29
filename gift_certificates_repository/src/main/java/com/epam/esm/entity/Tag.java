package com.epam.esm.entity;

import com.epam.esm.consts.NamedQueriesKeys;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Tag entity
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Entity(name = "tag")
@NamedQueries({
    @NamedQuery(name = NamedQueriesKeys.TAG_FIND_BY_NAME,
        query = "SELECT t FROM tag t WHERE t.name = :name"),
    @NamedQuery(name = NamedQueriesKeys.TAG_FIND_ALL,
        query = "SELECT t FROM tag t")
})
public class Tag extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

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
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Tag tag = (Tag) o;
    return Objects.equals(name, tag.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Tag{" + "id=" + id + ", name='" + name + '\'' + ", certificates=" + certificates + '}';
  }
}
