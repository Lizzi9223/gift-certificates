package com.epam.esm.exception.handling;

import com.epam.esm.consts.MessageKeysService;
import com.epam.esm.consts.MessagesKeysRepos;
import com.epam.esm.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * Contains methods to log and return exceptions<br>
 * in case of exception situation with order entities
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class OrderExceptions {
  public static final Logger logger = Logger.getLogger(UserExceptions.class);
  public final ResourceBundleMessageSource messageSource;

  @Autowired
  public OrderExceptions(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Returns exception to throw when <br>
   * order to add contains no certificates
   *
   * @return ServiceException
   */
  public ServiceException getOrderIsEmptyException() {
    logger.error("Order is empty");
    throw new ServiceException(
        messageSource.getMessage(
            MessageKeysService.EMPTY_ORDER, new Object[] {}, LocaleContextHolder.getLocale()));
  }

  /**
   * Returns exception to throw when <br>
   * order with the provided id does not exist
   *
   * @param id id of the order to find
   * @return ServiceException
   */
  public ServiceException getOrderIdNotExistException(Long id) {
    logger.error("Order {id ='" + id + "'} does not exist");
    return new ServiceException(
        messageSource.getMessage(
            MessagesKeysRepos.ORDER_ID_NOT_EXIST,
            new Object[] {id.toString()},
            LocaleContextHolder.getLocale()));
  }
}
