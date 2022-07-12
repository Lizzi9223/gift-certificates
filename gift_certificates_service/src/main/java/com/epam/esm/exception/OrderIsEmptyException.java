package com.epam.esm.exception;

/**
 * Thrown when order does not contain any certificates
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class OrderIsEmptyException extends RuntimeException {

  public OrderIsEmptyException() {}

  public OrderIsEmptyException(String message) {
    super(message);
  }

  public OrderIsEmptyException(String message, Throwable cause) {
    super(message, cause);
  }

  public OrderIsEmptyException(Throwable cause) {
    super(cause);
  }
}
