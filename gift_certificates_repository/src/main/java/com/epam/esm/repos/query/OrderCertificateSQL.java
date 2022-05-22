package com.epam.esm.repos.query;

/**
 * Contains SQL queries for operations with orders_has_gift_certificate table
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class OrderCertificateSQL {
  public static final String FIND_ORDER_CERTIFICATES =
      "select * from gift_certificate inner join "
          + "orders_has_gift_certificate on gift_certificate.id=orders_has_gift_certificate.gift_certificate_id  "
          + "where orders_has_gift_certificate.orders_id = :id";
}
