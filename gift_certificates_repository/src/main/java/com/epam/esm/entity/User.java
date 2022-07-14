package com.epam.esm.entity;

import com.epam.esm.consts.NamedQueriesKeys;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * User entity
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Entity(name = "users")
@NamedQueries({
    @NamedQuery(name = NamedQueriesKeys.USER_FIND_BY_LOGIN,
        query = "SELECT u FROM users u WHERE u.login = :login"),
    @NamedQuery(name = NamedQueriesKeys.USER_FIND_BY_LOGIN_AND_PASSWORD,
        query = "SELECT u FROM users u WHERE u.login = :login and u.password = :password"),
    @NamedQuery(name = NamedQueriesKeys.USER_FIND_ALL,
        query = "SELECT u FROM users u")
})
public class User extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

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

  public void addOrder(Order order) {
    orders.add(order);
    order.setUser(this);
  }

  public void removeOrder(Order order) {
    orders.remove(order);
    order.setUser(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(login, user.login);
  }

  @Override
  public int hashCode() {
    return Objects.hash(login);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", role=" + role +
        ", login='" + login + '\'' +
        ", password='" + password + '\'' +
        ", name='" + name + '\'' +
        ", orders=" + orders +
        '}';
  }
}
