package com.epam.esm.validator;

import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Validator for DTO
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
@ComponentScan("com.epam.esm")
public class DtoValidator {
  private static final Logger logger = Logger.getLogger(DtoValidator.class);
  private final Validator validator;

  @Autowired
  public DtoValidator(Validator validator) {
    this.validator = validator;
  }

  /**
   * Validates DTO object
   *
   * @param obj object to validate
   * @param clazz defines validation group
   * @param <T> object's class
   */
  public <T> void validate(T obj, Class clazz) {
    if (obj == null) {
      return;
    }

    Set<ConstraintViolation<T>> violations = validator.validate(obj, clazz);

    if (!CollectionUtils.isEmpty(violations)) {
      String className = obj.getClass().getSimpleName();
      String userMessage =
          violations.stream()
              .map(violation -> violation.getMessage())
              .collect(Collectors.joining("; "));
      String logMessage =
          "Invalid object: "
              + violations.stream()
                  .map(
                      violation ->
                          (className
                              + "."
                              + violation.getPropertyPath()
                              + " - "
                              + violation.getMessage()))
                  .collect(Collectors.joining("; "));
      logger.error(logMessage);
      throw new ValidationException(userMessage);
    }
  }
}
