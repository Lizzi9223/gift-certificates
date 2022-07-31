package com.epam.esm.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

/**
 * Tag DTO
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class TagDto extends RepresentationModel<TagDto> {
  private Long id;

  @NotBlank(message = "Name should not be blank")
  @Size(min = 2, max = 50, message = "Name length should be between 2 and 50")
  private String name;

  public TagDto() {}

  public TagDto(String name) {
    this.name = name;
  }

  public TagDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    TagDto tagDto = (TagDto) o;
    return Objects.equals(id, tagDto.id) && Objects.equals(name, tagDto.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, name);
  }

  @Override
  public String toString() {
    return "TagDTO{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
