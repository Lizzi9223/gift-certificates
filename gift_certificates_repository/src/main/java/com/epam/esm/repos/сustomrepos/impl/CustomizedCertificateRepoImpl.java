package com.epam.esm.repos.сustomrepos.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.repos.query.CertificateQuery;
import com.epam.esm.repos.сustomrepos.CustomizedCertificateRepo;
import com.epam.esm.search.model.SearchCriteria;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;

public class CustomizedCertificateRepoImpl implements CustomizedCertificateRepo {
  @PersistenceContext EntityManager entityManager;

  @Override
  public List findByCriteria(SearchCriteria searchCriteria, Pageable pageable) {
    return entityManager
        .createNativeQuery(CertificateQuery.getFindQuery(searchCriteria, pageable), Certificate.class)
        .getResultList();
  }

  @Override
  public List findExistingCertificates(Set certificates) {
    return entityManager
        .createNativeQuery(
            CertificateQuery.getQueryToFindExistingCertificates(certificates), Certificate.class)
        .getResultList();
  }
}
