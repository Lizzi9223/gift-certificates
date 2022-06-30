package com.epam.esm.search.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Contains params for certificate search
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class SearchCriteria {
  private String[] tagNames;
  private String name;
  private String description;
  private String sortByDateType;
  private String sortByNameType;

  public SearchCriteria() {}

  public SearchCriteria(
      String[] tagNames,
      String name,
      String description,
      String sortByDateType,
      String sortByNameType) {
    this.tagNames = tagNames;
    this.name = name;
    this.description = description;
    this.sortByDateType = sortByDateType;
    this.sortByNameType = sortByNameType;
  }

  public String[] getTagNames() {
    return tagNames;
  }

  public void setTagNames(String[] tagNames) {
    this.tagNames = tagNames;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSortByDateType() {
    return sortByDateType;
  }

  public void setSortByDateType(String sortByDateType) {
    this.sortByDateType = sortByDateType;
  }

  public String getSortByNameType() {
    return sortByNameType;
  }

  public void setSortByNameType(String sortByNameType) {
    this.sortByNameType = sortByNameType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchCriteria that = (SearchCriteria) o;
    return Arrays.equals(tagNames, that.tagNames) && Objects.equals(name,
        that.name) && Objects.equals(description, that.description)
        && Objects.equals(sortByDateType, that.sortByDateType) && Objects.equals(
        sortByNameType, that.sortByNameType);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(name, description, sortByDateType, sortByNameType);
    result = 31 * result + Arrays.hashCode(tagNames);
    return result;
  }

  @Override
  public String toString() {
    return "SearchCriteria{" +
        "tagNames=" + Arrays.toString(tagNames) +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", sortByDateType='" + sortByDateType + '\'' +
        ", sortByNameType='" + sortByNameType + '\'' +
        '}';
  }
}
