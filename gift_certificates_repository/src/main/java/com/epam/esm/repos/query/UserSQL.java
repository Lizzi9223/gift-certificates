package com.epam.esm.repos.query;

import org.apache.log4j.Logger;

/**
 * Contains SQL queries for operations with users table
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class UserSQL {
  private static final Logger logger = Logger.getLogger(UserSQL.class);
  public static final String FIND_BY_NAME = "from users where name = :name";
  public static final String FIND_ALL = "from users";
}
