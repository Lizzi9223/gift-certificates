package com.epam.esm.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * User entity
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Entity(name = "users")
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "roles_id")
  private Role role;

  private String login;

  private String password;

  private String name;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<Order> orders = new HashSet<>();

  public User() {}

  public User(Long id, Role role, String login, String password, String name, Set<Order> orders) {
    this.id = id;
    this.role = role;
    this.login = login;
    this.password = password;
    this.name = name;
    this.orders = orders;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Order> getOrders() {
    return orders;
  }

  public void setOrders(Set<Order> orders) {
    this.orders = orders;
  }
}
