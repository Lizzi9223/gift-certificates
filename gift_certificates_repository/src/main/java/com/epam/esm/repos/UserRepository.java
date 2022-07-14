package com.epam.esm.repos;

import com.epam.esm.consts.MessagesKeys;
import com.epam.esm.consts.NamedQueriesKeys;
import com.epam.esm.entity.User;
import com.epam.esm.repos.metadata.TableField;
import exception.ResourceNotFoundException;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Repository;

@Repository
@ComponentScan("com.epam.esm")
public class UserRepository {
  private static final Logger logger = Logger.getLogger(CertificateRepository.class);
  private final EntityManager entityManager;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public UserRepository(EntityManager entityManager, ResourceBundleMessageSource messageSource) {
    this.entityManager = entityManager;
    this.messageSource = messageSource;
  }

  public void create(User user) {
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(user);
      entityManager.getTransaction().commit();
    } catch (PersistenceException e) {
      entityManager.getTransaction().rollback();
      throw getExceptionForUserLoginAlreadyExists(e, user.getLogin());
    }
  }

  /**
   * Searches for user with provided name <br>
   * If user with provided name does not exist, {@code ResourceNotFoundException} is thrown
   *
   * @param login name of the user to search for
   * @return founded user
   */
  public User find(String login) {
    try {
      return entityManager
          .createNamedQuery(NamedQueriesKeys.USER_FIND_BY_LOGIN, User.class)
          .setParameter(TableField.LOGIN, login)
          .getSingleResult();
    } catch (NoResultException e) {
      throw getExceptionForUserLoginNotExist(e, login);
    }
  }

  /**
   * Searches for user with provided id <br>
   * If user with provided id does not exist, {@code ResourceNotFoundException} is thrown
   *
   * @param id id of the user to search for
   * @return founded user
   */
  public User find(Long id) {
    User user = entityManager.find(User.class, id);
    if (Objects.nonNull(user)) return user;
    else throw getExceptionForUserIdNotExist(null, id);
  }

  /**
   * Searches for all users
   *
   * @return list of all existing users
   */
  public List<User> findAll() {
    return entityManager
        .createNamedQuery(NamedQueriesKeys.USER_FIND_ALL, User.class)
        .getResultList();
  }

  private ResourceNotFoundException getExceptionForUserLoginNotExist(
      NoResultException e, String login) {
    logger.error("User {login='" + login + "'} does not exist");
    throw new ResourceNotFoundException(
        messageSource.getMessage(
            MessagesKeys.USER_LOGIN_NOT_EXIST,
            new Object[] {login},
            LocaleContextHolder.getLocale()),
        e);
  }

  private ResourceNotFoundException getExceptionForUserLoginAlreadyExists(
      PersistenceException e, String login) {
    logger.error("User {login='" + login + "'} already exists");
    throw new ResourceNotFoundException(
        messageSource.getMessage(
            MessagesKeys.USER_LOGIN_ALREADY_EXIST,
            new Object[] {login},
            LocaleContextHolder.getLocale()),
        e);
  }

  private ResourceNotFoundException getExceptionForUserIdNotExist(NoResultException e, Long id) {
    logger.error("User {id ='" + id + "'} does not exist");
    throw new ResourceNotFoundException(
        messageSource.getMessage(
            MessagesKeys.USER_ID_NOT_EXIST, new Object[] {id}, LocaleContextHolder.getLocale()),
        e);
  }
}
