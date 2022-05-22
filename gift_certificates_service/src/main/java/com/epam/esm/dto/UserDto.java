package com.epam.esm.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * User DTO
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class UserDto {
  private int id;

  @NotBlank(message = "Login should not be blank")
  @Size(min = 2, max = 20, message = "Login length should be between 2 and 20")
  private String login;

  @NotBlank(message = "Password should not be blank")
  @Size(min = 2, max = 20, message = "Password length should be between 2 and 20")
  private String password;

  @NotBlank(message = "Name should not be blank")
  @Size(min = 2, max = 20, message = "Name length should be between 2 and 20")
  private String name;

  public UserDto() {}

  public UserDto(int id, String login, String password, String name) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDto userDto = (UserDto) o;
    return id == userDto.id
        && Objects.equals(login, userDto.login)
        && Objects.equals(password, userDto.password)
        && Objects.equals(name, userDto.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, login, password, name);
  }

  @Override
  public String toString() {
    return "UserDto{"
        + "id="
        + id
        + ", login='"
        + login
        + '\''
        + ", password='"
        + password
        + '\''
        + ", name='"
        + name
        + '\''
        + '}';
  }
}
