package com.epam.esm.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
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

    if (Objects.nonNull(exception)) writeResponse("cause", exception.getMessage(), response);
    else writeResponse("error", authException.getMessage(), response);
  }

  private void writeResponse(String key, String message, HttpServletResponse response)
      throws IOException {
    byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap(key, message));
    response.getOutputStream().write(body);
  }
}
