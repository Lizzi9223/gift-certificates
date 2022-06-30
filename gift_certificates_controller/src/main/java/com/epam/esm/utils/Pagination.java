package com.epam.esm.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * Implements method for pagination
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class Pagination {
  private static final Logger logger = Logger.getLogger(Pagination.class);
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public Pagination(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Implements pagination
   *
   * @param objects result list of objects
   * @param page number of the page to display
   * @param pageSize number of elements per page
   * @return list containing certain number of object on the certain page
   */
  public List<?> paginate(List<?> objects, int page, int pageSize) {
    if (page < 1) {
      logger.error("Parameter page cannot be less than 1");
      throw new PaginationException(
          messageSource.getMessage(
              "message.controller.paginationParamsPage",
              new Object[] {page},
              LocaleContextHolder.getLocale()));
    }
    if (pageSize < 1) {
      logger.error("Parameter pageSize cannot be less than 1 [pageSize={0}]");
      throw new PaginationException(
          messageSource.getMessage(
              "message.controller.paginationParamsPageSize",
              new Object[] {pageSize},
              LocaleContextHolder.getLocale()));
    }
    return objects.stream()
        .skip((long) (page - 1) * pageSize)
        .limit(pageSize)
        .collect(Collectors.toList());
  }
}
