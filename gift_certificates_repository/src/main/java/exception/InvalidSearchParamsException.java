package exception;

/**
 * Thrown when search params are not valid
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class InvalidSearchParamsException extends RuntimeException {
  public InvalidSearchParamsException() {}

  public InvalidSearchParamsException(String message) {
    super(message);
  }

  public InvalidSearchParamsException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidSearchParamsException(Throwable cause) {
    super(cause);
  }
}
