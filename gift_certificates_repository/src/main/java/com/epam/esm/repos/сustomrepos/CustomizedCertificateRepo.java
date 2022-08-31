package com.epam.esm.repos.сustomrepos;

import com.epam.esm.search.model.SearchCriteria;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface CustomizedCertificateRepo<T> {
  List<T> findByCriteria(SearchCriteria searchCriteria, Pageable pageable);

  List<T> findExistingCertificates(Set<T> certificates);
}
