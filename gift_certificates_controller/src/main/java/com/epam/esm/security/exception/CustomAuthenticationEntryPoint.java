package com.epam.esm.security.exception;

import com.epam.esm.consts.Attributes;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Handles authentication exceptions
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    Exception exception = (Exception) request.getAttribute(Attributes.EXCEPTION);

    if (Objects.nonNull(exception))
      SecurityExceptionHandler.handle(exception.getMessage(), response);
    else SecurityExceptionHandler.handle(authException.getMessage(), response);
  }
}
