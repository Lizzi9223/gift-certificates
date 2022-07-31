package com.epam.esm.exception;

/**
 * Thrown when order does not contain any certificates
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class ServiceException extends RuntimeException {

  public ServiceException() {}

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceException(Throwable cause) {
    super(cause);
  }
}
