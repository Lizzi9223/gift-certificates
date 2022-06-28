package com.epam.esm.repos;

import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repos.metadata.TableField;
import com.epam.esm.repos.query.UserSQL;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
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
  @PersistenceContext private final EntityManager entityManager;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public UserRepository(EntityManager entityManager, ResourceBundleMessageSource messageSource) {
    this.entityManager = entityManager;
    this.messageSource = messageSource;
  }

  /**
   * Searches for user with provided name <br>
   * If user with provided name does not exist, {@code ResourceNotFoundException} is thrown
   *
   * @param name name of the user to search for
   * @return founded user
   */
  public Optional<User> find(String name) {
    try {
      return Optional.of(
          entityManager
              .createQuery(UserSQL.FIND_BY_NAME, User.class)
              .setParameter(TableField.NAME, name)
              .getSingleResult());
    } catch (NoResultException e) {
      logger.error("User {name='" + name + "'} does not exist");
      throw new ResourceNotFoundException(
          messageSource.getMessage(
              "message.repository.userNameNotExists",
              new Object[] {name},
              LocaleContextHolder.getLocale()),
          e);
    }
  }

  /**
   * Searches for user with provided id <br>
   * If user with provided id does not exist, {@code ResourceNotFoundException} is thrown
   *
   * @param id id of the user to search for
   * @return founded user
   */
  public Optional<User> find(int id) {
    try {
      return Optional.of(entityManager.find(User.class, id));
    } catch (NoResultException e) {
      logger.error("User {id ='" + id + "'} does not exist");
      throw new ResourceNotFoundException(
          messageSource.getMessage(
              "message.repository.userIdNotExists",
              new Object[] {id},
              LocaleContextHolder.getLocale()),
          e);
    }
  }

  /**
   * Searches for all users
   *
   * @return list of all existing users
   */
  public List<User> findAll() {
    return entityManager.createQuery(UserSQL.FIND_ALL, User.class).getResultList();
  }
}
