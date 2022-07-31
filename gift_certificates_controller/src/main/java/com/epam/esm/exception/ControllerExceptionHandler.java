package com.epam.esm.exception;

import com.epam.esm.consts.ExceptionCode;
import com.epam.esm.consts.URL;
import javax.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Handles application exceptions
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler({
    RepositoryException.class,
    ServiceException.class,
    NoHandlerFoundException.class,
    ValidationException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleBadRequestExceptions(Exception e, WebRequest request) {
    return responseEntityBuilder(e, request, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles NotFound
   *
   * @param e thrown exception
   * @param request received request
   * @return custom exception info and http status
   */
  @ExceptionHandler({NotFound.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse handleNotFoundException(Exception e, WebRequest request) {
    return responseEntityBuilder(e, request, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles HttpMediaTypeNotSupportedException
   *
   * @param e thrown exception
   * @param request received request
   * @return custom exception info and http status
   */
  @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  public ExceptionResponse handleHttpMediaTypeNotSupportedException(
      Exception e, WebRequest request) {
    return responseEntityBuilder(e, request, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  /**
   * Handles HttpRequestMethodNotSupportedException
   *
   * @param e thrown exception
   * @param request received request
   * @return custom exception info and http status
   */
  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  public ExceptionResponse handleHttpRequestMethodNotSupportedException(
      Exception e, WebRequest request) {
    return responseEntityBuilder(e, request, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleOtherExceptions(Exception e, WebRequest request) {
    return responseEntityBuilder(e, request, HttpStatus.BAD_REQUEST);
  }

  /**
   * Creates ResponseEntity
   *
   * @param e thrown exception
   * @param request received request
   * @param httpStatus http status to return
   * @return exception info and http status
   */
  private ExceptionResponse responseEntityBuilder(
      Exception e, WebRequest request, HttpStatus httpStatus) {
    ExceptionResponse exceptionResponse = new ExceptionResponse();
    e.printStackTrace();
    exceptionResponse.setErrorMessage(e.getMessage());
    String errorCode = String.valueOf(httpStatus.value());
    if (((ServletWebRequest) request)
        .getRequest()
        .getRequestURL()
        .toString()
        .contains(URL.TAG)) errorCode += ExceptionCode.TAG_CODE;
    else if (((ServletWebRequest) request)
        .getRequest()
        .getRequestURL()
        .toString()
        .contains(URL.ORDER)) errorCode += ExceptionCode.USER_CODE;
    else if (((ServletWebRequest) request)
        .getRequest()
        .getRequestURL()
        .toString()
        .contains(URL.USER)) errorCode += ExceptionCode.ORDER_CODE;
    else if (((ServletWebRequest) request)
        .getRequest()
        .getRequestURL()
        .toString()
        .contains(URL.CERTIFICATE)) errorCode += ExceptionCode.CERTIFICATE_CODE;
    else errorCode += ExceptionCode.AUTH_CODE;
    exceptionResponse.setErrorCode(errorCode);
    return exceptionResponse;
  }
}
