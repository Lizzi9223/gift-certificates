package com.epam.esm.repos.query;

import com.epam.esm.entity.Tag;
import java.util.Set;

/**
 * Contains SQL queries for operations with tag table
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public final class TagSQL {
  /**
   * Returns select query that looks for tags by provided names
   *
   * @param tags contains tags to find
   * @return string select query
   */
  public static String getQueryToFindExistingTags(Set<Tag> tags) {
    StringBuilder findQuery = new StringBuilder();
    findQuery.append("select * from tag where tag.name regexp '");
    for (Tag tag : tags) {
      findQuery.append(tag.getName()).append("|");
    }
    findQuery.deleteCharAt(findQuery.length() - 1);
    findQuery.append("'");
    return findQuery.toString();
  }
}
