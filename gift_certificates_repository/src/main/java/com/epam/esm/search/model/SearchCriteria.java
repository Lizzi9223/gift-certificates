package com.epam.esm.search.model;

import java.util.Objects;

/**
 * Contains params for certificate search
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class SearchCriteria {
  private String tagName;
  private String name;
  private String description;
  private String sortByDateType;
  private String sortByNameType;

  public SearchCriteria() {}

  public SearchCriteria(
      String tagName,
      String name,
      String description,
      String sortByDateType,
      String sortByNameType) {
    this.tagName = tagName;
    this.name = name;
    this.description = description;
    this.sortByDateType = sortByDateType;
    this.sortByNameType = sortByNameType;
  }

  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
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
    return Objects.equals(tagName, that.tagName)
        && Objects.equals(name, that.name)
        && Objects.equals(description, that.description)
        && Objects.equals(sortByDateType, that.sortByDateType)
        && Objects.equals(sortByNameType, that.sortByNameType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        tagName, name, description, sortByDateType, sortByNameType);
  }

  @Override
  public String toString() {
    return "SearchCriteria{" +
        "tagName='" + tagName + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", sortByDateType='" + sortByDateType + '\'' +
        ", sortByNameType='" + sortByNameType + '\'' +
        '}';
  }
}
