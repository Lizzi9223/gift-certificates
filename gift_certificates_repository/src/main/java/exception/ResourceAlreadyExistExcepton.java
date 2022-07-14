package exception;

/**
 * Thrown when such object already exists in database
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class ResourceAlreadyExistExcepton extends RuntimeException {
  public ResourceAlreadyExistExcepton() {}

  public ResourceAlreadyExistExcepton(String message) {
    super(message);
  }

  public ResourceAlreadyExistExcepton(String message, Throwable cause) {
    super(message, cause);
  }

  public ResourceAlreadyExistExcepton(Throwable cause) {
    super(cause);
  }
}
