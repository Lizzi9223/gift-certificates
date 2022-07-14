package com.epam.esm.repos;

import com.epam.esm.consts.MessagesKeys;
import com.epam.esm.consts.NamedQueriesKeys;
import com.epam.esm.entity.Role;
import com.epam.esm.repos.metadata.TableField;
import exception.ResourceNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Repository;

@Repository
@ComponentScan("com.epam.esm")
public class RoleRepository {
  private static final Logger logger = Logger.getLogger(RoleRepository.class);
  private final EntityManager entityManager;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public RoleRepository(EntityManager entityManager, ResourceBundleMessageSource messageSource) {
    this.entityManager = entityManager;
    this.messageSource = messageSource;
  }

  public Role findByName(String name) {
    try {
      return entityManager
          .createNamedQuery(NamedQueriesKeys.ROLE_FIND_BY_NAME, Role.class)
          .setParameter(TableField.NAME, name)
          .getSingleResult();
    } catch (NoResultException e) {
      throw getExceptionForRoleNameNotExist(e, name);
    }
  }

  private ResourceNotFoundException getExceptionForRoleNameNotExist(
      NoResultException e, String name) {
    logger.error("Role {name='" + name + "'} does not exist");
    throw new ResourceNotFoundException(
        messageSource.getMessage(
            MessagesKeys.ROLE_NAME_NOT_EXIST, new Object[] {name}, LocaleContextHolder.getLocale()),
        e);
  }
}
