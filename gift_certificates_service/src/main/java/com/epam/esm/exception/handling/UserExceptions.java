package com.epam.esm.exception.handling;

import com.epam.esm.consts.MessagesKeysRepos;
import com.epam.esm.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

/**
 * Contains methods to log and return exceptions<br>
 * in case of exception situation with user entities
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class UserExceptions {
  public static final Logger logger = Logger.getLogger(UserExceptions.class);
  public ResourceBundleMessageSource messageSource;

  @Autowired
  public UserExceptions(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Returns {@code ServiceException} when user not found by id
   *
   * @param id id of a user to find
   * @return ServiceException when user not found by id
   */
  public ServiceException getExceptionForUserIdNotExist(Long id) {
    logger.error("User {id ='" + id + "'} does not exist");
    throw new ServiceException(
        messageSource.getMessage(
            MessagesKeysRepos.USER_ID_NOT_EXIST,
            new Object[] {id.toString()},
            LocaleContextHolder.getLocale()));
  }

  /**
   * Returns exception to throw when <br>
   * user with the provided login already exists
   *
   * @param e DataIntegrityViolationException
   * @param login login of the user to create
   * @return ServiceException
   */
  public ServiceException getExceptionForUserLoginAlreadyExists(
      DataIntegrityViolationException e, String login) {
    logger.error("Attempt to create user with login '" + login + "' got error: " + e.getCause());
    throw new ServiceException(
        messageSource.getMessage(
            MessagesKeysRepos.USER_CREATION_FAILED,
            new Object[] {login},
            LocaleContextHolder.getLocale()),
        e);
  }

  /**
   * Returns {@code ServiceException} when user not found by login
   *
   * @param login login of a user to find
   * @return ServiceException when user not found by login
   */
  public ServiceException getExceptionForUserLoginNotExist(String login) {
    logger.error("User {login='" + login + "'} does not exist");
    throw new ServiceException(
        messageSource.getMessage(
            MessagesKeysRepos.USER_LOGIN_NOT_EXIST,
            new Object[] {login},
            LocaleContextHolder.getLocale()));
  }
}
