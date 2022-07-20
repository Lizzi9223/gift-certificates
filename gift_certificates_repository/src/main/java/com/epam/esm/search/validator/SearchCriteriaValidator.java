package com.epam.esm.search.validator;

import com.epam.esm.search.enums.SortType;
import com.epam.esm.search.model.SearchCriteria;
import java.util.Objects;

/**
 * Search criteria validator
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class SearchCriteriaValidator {

  private SearchCriteriaValidator() {}

  /**
   * Validates {@code SearchCriteria}
   *
   * @param searchCriteria object to validate
   * @return true if provided object is valid, otherwise false
   */
  public static boolean isValid(SearchCriteria searchCriteria) {
    if (!isValidSortType(searchCriteria.getSortByDateType())) return false;
    if (!isValidSortType(searchCriteria.getSortByNameType())) return false;
    return true;
  }

  public static boolean isValidSortType(String sortType) {
    if (Objects.nonNull(sortType) && Objects.isNull(SortType.getEnumByValue(sortType)))
      return false;
    return true;
  }
}
