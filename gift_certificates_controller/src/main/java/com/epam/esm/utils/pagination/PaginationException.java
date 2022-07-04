package com.epam.esm.utils.pagination;

/**
 * Thrown when params for pagination are not valid
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class PaginationException extends RuntimeException {

  public PaginationException() {}

  public PaginationException(String message) {
    super(message);
  }

  public PaginationException(String message, Throwable cause) {
    super(message, cause);
  }

  public PaginationException(Throwable cause) {
    super(cause);
  }
}
