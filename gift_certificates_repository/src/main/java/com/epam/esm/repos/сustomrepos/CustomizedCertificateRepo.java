package com.epam.esm.repos.сustomrepos;

import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomizedCertificateRepo<T> {
  Page<T> findByParams(String[] tagNames, String name, Pageable pageable);

  List<T> findExistingCertificates(Set<T> certificates);
}
