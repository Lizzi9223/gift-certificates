package com.epam.esm.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  private static final org.apache.log4j.Logger logger =
      Logger.getLogger(CustomAccessDeniedHandler.class);

  @Override
  public void handle(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc)
      throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    String message;
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      logger.warn(
          "User: "
              + auth.getName()
              + " attempted to access the protected URL: "
              + request.getRequestURI());
    }
    if (exc.getCause() != null) {
      message = exc.getCause().toString() + " " + exc.getMessage();
    } else {
      message = exc.getMessage();
    }
    byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
    response.getOutputStream().write(body);
  }
}
