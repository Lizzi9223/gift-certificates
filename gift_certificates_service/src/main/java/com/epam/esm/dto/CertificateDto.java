package com.epam.esm.dto;

import com.epam.esm.validator.group.CreateInfo;
import com.epam.esm.validator.group.UpdateInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

/**
 * Certificate DTO
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class CertificateDto extends RepresentationModel<CertificateDto>{
  private Long id;

  @NotBlank(groups = CreateInfo.class, message = "Name should not be blank")
  @Size(
      min = 2,
      max = 50,
      groups = {CreateInfo.class, UpdateInfo.class},
      message = "Name length should be between 2 and 50")
  private String name;

  @NotBlank(groups = CreateInfo.class, message = "Description should not be blank")
  @Size(
      max = 300,
      groups = {CreateInfo.class, UpdateInfo.class},
      message = "Description length should be max 300")
  private String description;

  @NotNull(groups = CreateInfo.class, message = "Price should not be blank")
  private BigDecimal price;

  @Min(value = 1, groups = CreateInfo.class, message = "Duration cannot be less than 1")
  private int duration;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private LocalDateTime createDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private LocalDateTime lastUpdateDate;

  private List<TagDto> tags;

  public CertificateDto() {}

  public CertificateDto(
      Long id,
      String name,
      String description,
      BigDecimal price,
      int duration,
      LocalDateTime createDate,
      LocalDateTime lastUpdateDate,
      List<TagDto> tags) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.duration = duration;
    this.createDate = createDate;
    this.lastUpdateDate = lastUpdateDate;
    this.tags = tags;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public LocalDateTime getLastUpdateDate() {
    return lastUpdateDate;
  }

  public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }

  public List<TagDto> getTags() {
    return tags;
  }

  public void setTags(List<TagDto> tags) {
    this.tags = tags;
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
    CertificateDto that = (CertificateDto) o;
    return duration == that.duration && Objects.equals(id, that.id)
        && Objects.equals(name, that.name) && Objects.equals(description,
        that.description) && Objects.equals(price, that.price) && Objects.equals(
        createDate, that.createDate) && Objects.equals(lastUpdateDate, that.lastUpdateDate)
        && Objects.equals(tags, that.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, name, description, price, duration, createDate,
        lastUpdateDate, tags);
  }

  @Override
  public String toString() {
    return "CertificateDTO{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", price="
        + price
        + ", duration="
        + duration
        + ", createDate="
        + createDate
        + ", lastUpdateDate="
        + lastUpdateDate
        + ", tags="
        + tags
        + '}';
  }
}
