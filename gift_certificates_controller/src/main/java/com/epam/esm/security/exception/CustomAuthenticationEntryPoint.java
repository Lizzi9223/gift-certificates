package com.epam.esm.security.exception;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    Exception exception = (Exception) request.getAttribute("exception");

    if (Objects.nonNull(exception))
      SecurityExceptionHandler.handle(exception.getMessage(), response);
    else SecurityExceptionHandler.handle(authException.getMessage(), response);
  }
}
