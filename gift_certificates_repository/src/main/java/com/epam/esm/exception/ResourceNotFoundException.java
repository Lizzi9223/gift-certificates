package com.epam.esm.exception;

/**
 * Thrown when this object does not exist in database
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException() {}

  public ResourceNotFoundException(String message) {
    super(message);
  }

  public ResourceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceNotFoundException(Throwable cause) {
    super(cause);
  }
}
