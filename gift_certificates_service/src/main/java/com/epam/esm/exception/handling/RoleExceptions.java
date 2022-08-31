package com.epam.esm.exception.handling;

import com.epam.esm.consts.MessagesKeysRepos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * Contains methods to log and return exceptions<br>
 * in case of exception situation with role entities
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class RoleExceptions {
  public static final Logger logger = Logger.getLogger(UserExceptions.class);
  public final ResourceBundleMessageSource messageSource;

  @Autowired
  public RoleExceptions(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Returns {@code ServiceException} when role not found by name
   *
   * @param name name of a role to find
   * @return ServiceException when role not found by name
   */
  public SecurityException getExceptionForRoleNameNotExist(String name) {
    logger.error("Role {name='" + name + "'} does not exist");
    throw new SecurityException(
        messageSource.getMessage(
            MessagesKeysRepos.ROLE_NAME_NOT_EXIST,
            new Object[] {name},
            LocaleContextHolder.getLocale()));
  }
}
