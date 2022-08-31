package com.epam.esm.repos.сustomrepos.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repos.query.TagQuery;
import com.epam.esm.repos.сustomrepos.CustomizedTagRepo;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomizedTagRepoImpl implements CustomizedTagRepo {
  @PersistenceContext EntityManager entityManager;

  @Override
  public List findExistingTags(Set tags) {
    return entityManager
        .createNativeQuery(TagQuery.getQueryToFindExistingTags(tags), Tag.class)
        .getResultList();
  }
}
