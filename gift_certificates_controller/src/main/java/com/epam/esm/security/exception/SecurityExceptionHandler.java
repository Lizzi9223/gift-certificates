package com.epam.esm.security.exception;

import com.epam.esm.consts.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

class SecurityExceptionHandler {
  static void handle(String message, HttpServletResponse response) throws IOException {
    String errorCode = HttpStatus.FORBIDDEN.value() + ExceptionCode.AUTH_CODE;
    byte[] body =
        new ObjectMapper().writeValueAsBytes(Map.of("errorCode", errorCode, "error", message));
    response.getOutputStream().write(body);
  }
}
