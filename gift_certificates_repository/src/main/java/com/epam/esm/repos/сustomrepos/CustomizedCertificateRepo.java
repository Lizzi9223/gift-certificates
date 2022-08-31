package com.epam.esm.repos.сustomrepos;

import com.epam.esm.search.model.SearchCriteria;
import java.util.List;
import java.util.Set;

public interface CustomizedCertificateRepo<T> {
  List<T> findByCriteria(SearchCriteria searchCriteria);

  List<T> findExistingCertificates(Set<T> certificates);
}
