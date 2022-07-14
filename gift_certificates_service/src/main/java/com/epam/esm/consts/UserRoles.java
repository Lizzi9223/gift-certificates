package com.epam.esm.consts;

public enum UserRoles {
  ADMIN ("ROLE_ADMIN"),
  USER("ROLE_USER");
  public final String roleName;

  private UserRoles(String roleName) {
    this.roleName = roleName;
  }
}
