package com.epam.esm.entity;

import com.epam.esm.entity.key.CertificateTagId;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name = "gift_certificate_has_tag")
public class CertificateTag {
  @EmbeddedId
  private CertificateTagId certificateTagId;

  public CertificateTag() {
  }

  public CertificateTag(CertificateTagId certificateTagId) {
    this.certificateTagId = certificateTagId;
  }

  public CertificateTagId getCertificateTagId() {
    return certificateTagId;
  }

  public void setCertificateTagId(CertificateTagId certificateTagId) {
    this.certificateTagId = certificateTagId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CertificateTag that = (CertificateTag) o;
    return Objects.equals(certificateTagId, that.certificateTagId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(certificateTagId);
  }

  @Override
  public String toString() {
    return "CertificateTag{" +
        "certificateTagId=" + certificateTagId +
        '}';
  }
}
