package com.epam.esm.repos.query;

/**
 * Contains SQL queries for operations with orders table
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class OrderSQL {
  public static final String FIND_ALL = "from orders";
  public static final String FIND_BY_USER = "from orders where users_id = :id";
}
