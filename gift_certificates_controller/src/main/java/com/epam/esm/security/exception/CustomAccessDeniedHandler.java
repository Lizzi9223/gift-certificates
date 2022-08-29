package com.epam.esm.security.exception;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Handles situations when unauthorized user <br>
 * or user without enough authorities <br>
 * attempts to access resource
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  private static final org.apache.log4j.Logger logger =
      Logger.getLogger(CustomAccessDeniedHandler.class);

  @Override
  public void handle(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
      throws IOException {
    String message = exc.getMessage();
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.nonNull(auth)) {
      logger.warn(
          "User: "
              + auth.getName()
              + " attempted to access the protected URL: "
              + request.getRequestURI());
    }
    SecurityExceptionHandler.handle(message, response);
  }
}
