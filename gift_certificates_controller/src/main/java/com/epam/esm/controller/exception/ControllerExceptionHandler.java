package com.epam.esm.controller.exception;

import javax.validation.ValidationException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
  private static final Logger logger = Logger.getLogger(ControllerExceptionHandler.class);

  //  @ExceptionHandler({ResourceNotFoundException.class})
  //  public ExceptionResponse handleResourceNotFoundException(Exception e, WebRequest request) {
  //    return responseEntityBuilder(e, request, HttpStatus.BAD_REQUEST);
  //  }
  //
  //  @ExceptionHandler({ResourceAlreadyExistExcepton.class})
  //  public ExceptionResponse handleResourceAlreadyExistExcepton(Exception e, WebRequest request) {
  //    return responseEntityBuilder(e, request, HttpStatus.BAD_REQUEST);
  //  }
  //
  //  @ExceptionHandler({InvalidSearchParamsException.class})
  //  public ExceptionResponse handlerInvalidSearchParamsException(Exception e, WebRequest request)
  // {
  //    return responseEntityBuilder(e, request, HttpStatus.BAD_REQUEST);
  //  }

  @ExceptionHandler({NoHandlerFoundException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleNoHandlerFoundException(Exception e, WebRequest request) {
    return responseEntityBuilder(e, request, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ValidationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handlerValidationException(Exception e, WebRequest request) {
    return responseEntityBuilder(e, request, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles ResourceNotFoundException
   *
   * @param e thrown exception
   * @param request received request
   * @return custom exception info and http status
   */
  @ExceptionHandler({NotFound.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse handleResourceNotFoundException(Exception e, WebRequest request) {
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
  public ExceptionResponse handleHttpBadRequestStatus(Exception e, WebRequest request) {
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
    exceptionResponse.setErrorMessage(e.getMessage());
    String errorCode = String.valueOf(httpStatus.value());
    String CERTIFICATE_CODE = "01";
    String TAG_CODE = "02";
    String TAG = "tag";
    if (((ServletWebRequest) request).getRequest().getRequestURL().toString().contains(TAG))
      errorCode += TAG_CODE;
    else errorCode += CERTIFICATE_CODE;
    exceptionResponse.setErrorCode(errorCode);
    return exceptionResponse;
  }
}
