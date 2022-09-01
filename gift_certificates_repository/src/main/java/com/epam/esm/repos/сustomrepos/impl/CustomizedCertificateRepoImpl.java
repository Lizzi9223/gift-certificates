package com.epam.esm.repos.сustomrepos.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.repos.query.CertificateQuery;
import com.epam.esm.repos.сustomrepos.CustomizedCertificateRepo;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CustomizedCertificateRepoImpl implements CustomizedCertificateRepo {
  @PersistenceContext EntityManager entityManager;

  @Override
  public Page findByParams(String[] tagNames, String name, Pageable pageable) {
    String query = CertificateQuery.getFindByParamsQuery(tagNames, name, pageable);
    List<Certificate> content =
        entityManager.createNativeQuery(query, Certificate.class).getResultList();
    String countQuery = "select count(*) from (" + query + ") c";
    int totalSize = entityManager.createNativeQuery(countQuery).getFirstResult();
    return new PageImpl<>(content, pageable, totalSize);
  }

  @Override
  public List findExistingCertificates(Set certificates) {
    return entityManager
        .createNativeQuery(
            CertificateQuery.getQueryToFindExistingCertificates(certificates), Certificate.class)
        .getResultList();
  }
}
