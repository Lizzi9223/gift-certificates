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
 * in case of exception situation with tag entities
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class TagExceptions {
  public static final Logger logger = Logger.getLogger(UserExceptions.class);
  public final ResourceBundleMessageSource messageSource;

  @Autowired
  public TagExceptions(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Returns {@code ServiceException} when tag not found by id
   *
   * @param id id of a tag to find
   * @return ServiceException when tag not found by id
   */
  public ServiceException getExceptionForTagIdNotExist(Long id) {
    logger.error("Tag {id=" + id + "} does not exist");
    throw new ServiceException(
        messageSource.getMessage(
            MessagesKeysRepos.TAG_ID_NOT_EXIST,
            new Object[] {id.toString()},
            LocaleContextHolder.getLocale()));
  }

  /**
   * Returns exception to throw when <br>
   * tag with the provided name already exists
   *
   * @param e DataIntegrityViolationException
   * @param name name of the tag to create
   * @return ServiceException
   */
  public ServiceException getExceptionForTagNameAlreadyExist(
      DataIntegrityViolationException e, String name) {
    logger.error(
        "Attempt to create tag with name '" + name + "' got error: " + e.getCause());
    throw new ServiceException(
        messageSource.getMessage(
            MessagesKeysRepos.TAG_CREATION_FAILED,
            new Object[] {name},
            LocaleContextHolder.getLocale()),
        e);
  }

  /**
   * Returns {@code ServiceException} when tag not found by name
   *
   * @param name name of a tag to find
   * @return ServiceException when tag not found by name
   */
  public ServiceException getExceptionForTagNameNotExist(String name) {
    logger.error("Tag {name='" + name + "'} does not exist");
    throw new ServiceException(
        messageSource.getMessage(
            MessagesKeysRepos.TAG_NAME_NOT_EXIST,
            new Object[] {name},
            LocaleContextHolder.getLocale()));
  }
}
