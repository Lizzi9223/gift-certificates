package com.epam.esm.exception;

/**
 * Thrown when this object does not exist in database
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class RepositoryException extends RuntimeException {
  public RepositoryException() {}

  public RepositoryException(String message) {
    super(message);
  }

  public RepositoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public RepositoryException(Throwable cause) {
    super(cause);
  }
}
