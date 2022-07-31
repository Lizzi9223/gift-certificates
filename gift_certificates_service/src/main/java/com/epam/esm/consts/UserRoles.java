package com.epam.esm.consts;

public enum UserRoles {
  ADMIN("ROLE_ADMIN"),
  USER("ROLE_USER");
  private final String roleName;

  UserRoles(String roleName) {
    this.roleName = roleName;
  }

  @Override
  public String toString() {
    return roleName;
  }
}
