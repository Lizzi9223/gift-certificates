package com.epam.esm.repos.query;

import com.epam.esm.repos.metadata.TableField;
import com.epam.esm.entity.Certificate;
import com.epam.esm.search.model.SearchCriteria;
import java.util.HashMap;
import java.util.Map;
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
  public static final String INSERT =
      "insert into gift_certificate values (default, ?, ?, ?, ?, ?, ?)";
  public static final String DELETE = "delete from gift_certificate where id = :id";

  /**
   * Returns update query for certificates
   *
   * @param certificate contains info for update
   * @return string update query
   */
  public static String GET_UPDATE_QUERY(Certificate certificate) {
    String updateQuery = "update gift_certificate set %s where id = :id";
    return String.format(updateQuery, GET_UPDATE_QUERY_PARAMS(certificate));
  }

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
    if (!Objects.isNull(searchCriteria.getTagName())) {
      findQuery.append(" where tag.name='").append(searchCriteria.getTagName()).append("'");
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
      findQuery.append(" order by gift_certificate.create_date ").append(searchCriteria.getSortByDateType());
      isOrderClauseAdded = true;
    }
    if (Objects.nonNull(searchCriteria.getSortByNameType())) {
      if(isOrderClauseAdded) findQuery.append(",");
      else findQuery.append(" order by");
      findQuery.append(" gift_certificate.name ").append(searchCriteria.getSortByNameType());
    }
    return findQuery.toString();
  }

  /**
   * Returns part of update query that contains fields to update and their new values
   *
   * @param certificate contains info for update
   * @return string with fields to update and their new values
   */
  private static String GET_UPDATE_QUERY_PARAMS(Certificate certificate) {
    StringBuilder params = new StringBuilder();
    Map<String, Object> fields =
        new HashMap<String, Object>() {
          {
            put(TableField.NAME, certificate.getName());
            put(TableField.DESCRIPTION, certificate.getDescription());
            put(TableField.PRICE, certificate.getPrice());
            put(TableField.DURATION, Integer.valueOf(certificate.getDuration()));
            put(TableField.CREATE_DATE, certificate.getCreateDate());
            put(TableField.LAST_UPDATE_DATE, certificate.getLastUpdateDate());
          }
        };
    fields.forEach(
        (key, value) -> {
          if (!Objects.isNull(value)) {
            params.append(key).append("='").append(value).append("',");
          }
        });
    params.deleteCharAt(params.length() - 1);
    return params.toString();
  }
}
