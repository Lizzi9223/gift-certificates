package com.epam.esm.security.exception;

import com.epam.esm.consts.Attributes;
import com.epam.esm.consts.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * Generates exceptions output
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
class SecurityExceptionHandler {
  static void handle(String message, HttpServletResponse response) throws IOException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    String errorCode = HttpStatus.FORBIDDEN.value() + ExceptionCode.AUTH_CODE;
    byte[] body =
        new ObjectMapper()
            .writeValueAsBytes(Map.of(Attributes.ERROR, message, Attributes.ERROR_CODE, errorCode));
    response.getOutputStream().write(body);
  }
}
