package com.epam.esm.search.enums;

import java.util.stream.Stream;

/**
 * Possible types of sorting
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public enum SortType {
  ASC("ASC"),
  DESC("DESC");

  private final String value;

  SortType(String value) {
    this.value = value;
  }

  public static SortType getEnumByValue(String value) {
    return Stream.of(SortType.values())
        .filter(v -> v.getValue().equals(value))
        .findFirst()
        .orElse(null);
  }

  public String getValue() {
    return value;
  }
}
