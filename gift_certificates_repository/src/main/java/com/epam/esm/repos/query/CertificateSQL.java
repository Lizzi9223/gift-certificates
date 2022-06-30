package com.epam.esm.repos.query;

import com.epam.esm.search.model.SearchCriteria;
import java.util.Objects;
import org.apache.log4j.Logger;

/**
 * Contains SQL queries for operations with gift_certificate table
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public final class CertificateSQL {
  private static final Logger logger = Logger.getLogger(CertificateSQL.class);
  public static final String FIND_BY_NAME = "from gift_certificate where name = :name";

  /**
   * Returns select query for certificates
   *
   * @param searchCriteria contains search params
   * @return string select query
   */
  public static String GET_FIND_QUERY(SearchCriteria searchCriteria) {
    StringBuilder findQuery = new StringBuilder();
    findQuery.append(
        "select distinct gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date from gift_certificate \n"
            + "left outer join gift_certificate_has_tag on gift_certificate.id=gift_certificate_has_tag.gift_certificate_id\n"
            + "left outer join tag on tag.id=gift_certificate_has_tag.tag_id");
    boolean isWhereClauseAdded = false;
    boolean isOrderClauseAdded = false;
    if (!Objects.isNull(searchCriteria.getTagNames())) {
      findQuery.append(" where tag.name regexp '");
      for (String tagName : searchCriteria.getTagNames()) {
        findQuery.append(tagName).append("|");
      }
      findQuery.deleteCharAt(findQuery.length() - 1);
      findQuery
          .append("' group by gift_certificate.name having count(*) = ")
          .append(searchCriteria.getTagNames().length);
      isWhereClauseAdded = true;
    }
    if (Objects.nonNull(searchCriteria.getName())) {
      if (!isWhereClauseAdded) {
        findQuery.append(" where ");
        isWhereClauseAdded = true;
      } else findQuery.append(" and");
      findQuery
          .append(" gift_certificate.name like '%")
          .append(searchCriteria.getName())
          .append("%'");
    }
    if (Objects.nonNull(searchCriteria.getDescription())) {
      if (!isWhereClauseAdded) findQuery.append(" where ");
      else findQuery.append(" and");
      findQuery
          .append(" gift_certificate.description like '%")
          .append(searchCriteria.getDescription())
          .append("%'");
    }
    if (Objects.nonNull(searchCriteria.getSortByDateType())) {
      findQuery
          .append(" order by gift_certificate.create_date ")
          .append(searchCriteria.getSortByDateType());
      isOrderClauseAdded = true;
    }
    if (Objects.nonNull(searchCriteria.getSortByNameType())) {
      if (isOrderClauseAdded) findQuery.append(",");
      else findQuery.append(" order by");
      findQuery.append(" gift_certificate.name ").append(searchCriteria.getSortByNameType());
    }
    return findQuery.toString();
  }
}
