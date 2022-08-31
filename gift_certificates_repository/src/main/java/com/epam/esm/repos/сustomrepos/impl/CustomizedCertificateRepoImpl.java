package com.epam.esm.repos.сustomrepos.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.repos.query.CertificateQuery;
import com.epam.esm.repos.сustomrepos.CustomizedCertificateRepo;
import com.epam.esm.search.model.SearchCriteria;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomizedCertificateRepoImpl implements CustomizedCertificateRepo {
  @PersistenceContext EntityManager entityManager;

  @Override
  public List findByCriteria(SearchCriteria searchCriteria) {
    return entityManager
        .createNativeQuery(CertificateQuery.getFindQuery(searchCriteria), Certificate.class)
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
