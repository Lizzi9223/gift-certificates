package com.epam.esm.repos.query;

/**
 * Contains SQL queries for operations with tag table
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class TagSQL {
  public static final String FIND_ALL = "from tag";
  public static final String FIND_BY_NAME = "from tag where name = :name";
}
