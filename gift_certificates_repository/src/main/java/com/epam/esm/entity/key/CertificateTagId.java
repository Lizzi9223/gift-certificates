package com.epam.esm.entity.key;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CertificateTagId implements Serializable {
  @Column(name = "gift_certificate_id")
  private int certificateId;

  @Column(name = "tag_id")
  private int tagId;

  public CertificateTagId() {}

  public CertificateTagId(int certificateId, int tagId) {
    this.certificateId = certificateId;
    this.tagId = tagId;
  }

  public int getCertificateId() {
    return certificateId;
  }

  public void setCertificateId(int certificateId) {
    this.certificateId = certificateId;
  }

  public int getTagId() {
    return tagId;
  }

  public void setTagId(int tagId) {
    this.tagId = tagId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CertificateTagId that = (CertificateTagId) o;
    return certificateId == that.certificateId && tagId == that.tagId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(certificateId, tagId);
  }

  @Override
  public String toString() {
    return "CertificateTag{" + "certificateId=" + certificateId + ", tagId=" + tagId + '}';
  }
}
