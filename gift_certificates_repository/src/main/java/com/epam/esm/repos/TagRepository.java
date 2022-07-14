package com.epam.esm.repos;

import com.epam.esm.consts.MessagesKeys;
import com.epam.esm.consts.NamedQueriesKeys;
import com.epam.esm.entity.Tag;
import exception.ResourceAlreadyExistExcepton;
import exception.ResourceNotFoundException;
import com.epam.esm.repos.metadata.TableField;
import com.epam.esm.repos.query.TagSQL;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
public class TagRepository {
  private static final Logger logger = Logger.getLogger(CertificateRepository.class);
  private final EntityManager entityManager;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public TagRepository(EntityManager entityManager, ResourceBundleMessageSource messageSource) {
    this.entityManager = entityManager;
    this.messageSource = messageSource;
  }

  /**
   * Creates new tag <br>
   * If tag with provided name already exists, {@code ResourceAlreadyExistExcepton} is thrown
   *
   * @param tag tag to create
   * @return id of the created tag
   */
  public void create(Tag tag) {
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(tag);
      entityManager.getTransaction().commit();
    } catch (PersistenceException e) {
      entityManager.getTransaction().rollback();
      throw getExceptionForTagNameAlreadyExist(tag.getName());
    }
  }

  /**
   * Deletes tag by id <br>
   * If this tag is added to some certificate, it will be excluded from it with no exception
   *
   * @param id id of the tag to delete
   */
  public void delete(Long id) {
    Tag tag = entityManager.find(Tag.class, id);
    if (Objects.nonNull(tag)) {
      entityManager.getTransaction().begin();
      entityManager.remove(tag);
      entityManager.getTransaction().commit();
    } else throw getExceptionForTagIdNotExist(null, id);
  }

  /**
   * Searches for tag by name <br>
   * Throws {@code EmptyResultDataAccessException} if tag with provided name does not exist
   *
   * @param name name of the tag to find
   * @return founded tag
   */
  public Tag findByName(String name) {
    try {
      return entityManager
          .createNamedQuery(NamedQueriesKeys.TAG_FIND_BY_NAME, Tag.class)
          .setParameter(TableField.NAME, name)
          .getSingleResult();
    } catch (NoResultException e) {
      throw getExceptionForTagNameNotExist(e, name);
    }
  }

  /**
   * Searches for tag by id <br>
   * Throws {@code EmptyResultDataAccessException} if tag with provided id does not exist
   *
   * @param id id of the tag to find
   * @return founded tag
   */
  public Tag findById(Long id) {
    Tag tag = entityManager.find(Tag.class, id);
    if (Objects.nonNull(tag)) return tag;
    else throw getExceptionForTagIdNotExist(null, id);
  }

  /**
   * Searches for all tags
   *
   * @return list of all existing tags
   */
  public List<Tag> findAll() {
    return entityManager.createNamedQuery(NamedQueriesKeys.TAG_FIND_ALL, Tag.class).getResultList();
  }

  public List<Tag> findExistingTags(Set<Tag> tags) {
    return entityManager
        .createNativeQuery(TagSQL.GET_QUERY_TO_FIND_EXISTING_TAGS(tags), Tag.class)
        .getResultList();
  }

  private ResourceAlreadyExistExcepton getExceptionForTagNameAlreadyExist(String name) {
    logger.error("Tag {name='" + name + "'} already exists");
    throw new ResourceAlreadyExistExcepton(
        messageSource.getMessage(
            MessagesKeys.TAG_NAME_ALREADY_EXIST,
            new Object[] {name},
            LocaleContextHolder.getLocale()));
  }

  private ResourceNotFoundException getExceptionForTagIdNotExist(NoResultException e, Long id) {
    logger.error("Tag {id=" + id + "} does not exist");
    throw new ResourceNotFoundException(
        messageSource.getMessage(
            MessagesKeys.TAG_ID_NOT_EXIST, new Object[] {id}, LocaleContextHolder.getLocale()));
  }

  private ResourceNotFoundException getExceptionForTagNameNotExist(
      NoResultException e, String name) {
    logger.error("Tag {name='" + name + "'} does not exist");
    throw new ResourceNotFoundException(
        messageSource.getMessage(
            MessagesKeys.TAG_NAME_NOT_EXIST, new Object[] {name}, LocaleContextHolder.getLocale()),
        e);
  }
}
