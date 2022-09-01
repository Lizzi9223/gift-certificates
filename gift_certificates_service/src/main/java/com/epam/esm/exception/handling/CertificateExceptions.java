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
 * in case of exception situation with certificate entities
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class CertificateExceptions {
  public static final Logger logger = Logger.getLogger(UserExceptions.class);
  public final ResourceBundleMessageSource messageSource;

  @Autowired
  public CertificateExceptions(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Returns exception to throw when <br>
   * certificate with the provided id does not exist
   *
   * @param id id of the certificate to find
   * @return ServiceException
   */
  public ServiceException getExceptionForCertificateIdNotExist(Long id) {
    logger.error("Certificate [id='" + id + "'] does not exist");
    return new ServiceException(
        messageSource.getMessage(
            MessagesKeysRepos.CERTIFICATE_ID_NOT_EXIST,
            new Object[] {id.toString()},
            LocaleContextHolder.getLocale()),
        null);
  }

  /**
   * Returns exception to throw when <br>
   * certificate with the provided name does not exist
   *
   * @param name name of the certificate to find
   * @return ServiceException
   */
  public ServiceException getExceptionForCertificateNameNotExist(String name) {
    logger.error("Certificate [name='" + name + "'] does not exist");
    return new ServiceException(
        messageSource.getMessage(
            MessagesKeysRepos.CERTIFICATE_NAME_NOT_EXIST,
            new Object[] {name},
            LocaleContextHolder.getLocale()));
  }

  /**
   * Returns exception to throw when <br>
   * certificate with the provided name already exists
   *
   * @param e DataIntegrityViolationException
   * @param name name of the certificate to create/update
   * @return ServiceException
   */
  public ServiceException getExceptionForCertificateNameAlreadyExist(
      DataIntegrityViolationException e, String name) {
    logger.error(
        "Attempt to create or update certificate with name='"
            + name
            + "' got error: "
            + e.getCause());
    return new ServiceException(
        messageSource.getMessage(
            MessagesKeysRepos.CERTIFICATE_CREATION_FAILED,
            new Object[] {name},
            LocaleContextHolder.getLocale()),
        e);
  }
}
