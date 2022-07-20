package com.epam.esm.security.model;

/**
 * A model class to return the token on successful authentication
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class AuthResponse {
  private String token;

  public AuthResponse() {}

  public AuthResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
