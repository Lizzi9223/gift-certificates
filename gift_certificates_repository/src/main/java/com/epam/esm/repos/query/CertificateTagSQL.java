package com.epam.esm.repos.query;

/**
 * Contains SQL queries for operations with gift_certificate_has_tag table
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class CertificateTagSQL {
  public static final String FIND_CERTIFICATE_TAGS =
      "select * from tag inner join "
          + "gift_certificate_has_tag on tag.id=gift_certificate_has_tag.tag_id  "
          + "where gift_certificate_has_tag.gift_certificate_id = :id";
}
