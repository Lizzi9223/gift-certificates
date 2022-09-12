package com.epam.esm.repos.query;

import com.epam.esm.entity.Certificate;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Contains SQL queries for operations with gift_certificate table
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public final class CertificateQuery {
  /**
   * Returns select query for certificates
   *
   * @param tagNames names of the tags that certificates has to be attached to
   * @param name substring that certificate has to contain in its name or description
   * @param pageable for pagination implementation
   * @return string select query
   */
  public static String getFindByParamsQuery(String[] tagNames, String name, Pageable pageable) {
    StringBuilder findQuery = new StringBuilder();
    findQuery.append(
        "select distinct g.id, g.name, g.description, g.price, g.duration, g.create_date, g.last_update_date, "
            + "g.operation, g.timestamp from gift_certificate g \n"
            + "left outer join gift_certificate_has_tag ght on g.id=ght.gift_certificate_id\n"
            + "left outer join tag t on t.id=ght.tag_id");

    boolean isWhereClauseAdded = false;
    boolean isOrderClauseAdded = false;

    if (Objects.nonNull(tagNames)) {
      findQuery.append(" where t.name regexp '");
      for (String tagName : tagNames) {
        findQuery.append(tagName).append("|");
      }
      findQuery.deleteCharAt(findQuery.length() - 1);
      findQuery.append("' group by g.name having count(*) >= ").append(tagNames.length);
      isWhereClauseAdded = true;
    }

    if (Objects.nonNull(name)) {
      if (!isWhereClauseAdded) {
        findQuery.append(" where (");
        isWhereClauseAdded = true;
      } else findQuery.append(" and (");
      findQuery.append(" g.name like '%").append(name).append("%'");
    }

    if (Objects.nonNull(name)) {
      if (!isWhereClauseAdded) findQuery.append(" where ");
      else findQuery.append(" or");
      findQuery.append(" g.description like '%").append(name).append("%')");
    }

    for (Sort.Order order : pageable.getSort()) {
      if (!isOrderClauseAdded) {
        findQuery.append(" order by");
        isOrderClauseAdded = true;
      } else findQuery.append(",");
      findQuery.append(" g.").append(order.getProperty()).append(" ").append(order.getDirection());
    }

    return findQuery.toString();
  }

  /**
   * Returns select query that looks for certificates by provided names
   *
   * @param certificates contains certificates to find
   * @return string select query
   */
  public static String getQueryToFindExistingCertificates(Set<Certificate> certificates) {
    StringBuilder findQuery = new StringBuilder();
    findQuery.append("select * from gift_certificate where gift_certificate.name regexp '");
    for (Certificate certificate : certificates) {
      findQuery.append(certificate.getName()).append("|");
    }
    findQuery.deleteCharAt(findQuery.length() - 1);
    findQuery.append("'");
    return findQuery.toString();
  }
}
