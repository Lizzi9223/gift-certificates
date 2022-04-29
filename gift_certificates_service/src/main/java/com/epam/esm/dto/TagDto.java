package com.epam.esm.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Tag DTO
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class TagDto {
  private int id;

  @NotBlank(message = "Name should not be blank")
  @Size(min = 2, max = 50, message = "Name length should be between 2 and 50")
  private String name;

  public TagDto() {}

  public TagDto(String name) {
    this.name = name;
  }

  public TagDto(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TagDto tagDTO = (TagDto) o;
    return id == tagDTO.id && Objects.equals(name, tagDTO.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "TagDTO{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
