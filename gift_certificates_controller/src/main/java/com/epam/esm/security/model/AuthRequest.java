package com.epam.esm.security.model;

/**
 * The JSON input request provided by the user <br>
 * will be unmarshalled to Java Object using this class.
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class AuthRequest {
  private String login;
  private String password;

  public AuthRequest() {}

  public AuthRequest(String login, String password) {
    this.login = login;
    this.password = password;
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
}
