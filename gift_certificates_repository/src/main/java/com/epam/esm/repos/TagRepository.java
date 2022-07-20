package com.epam.esm.repos;

import com.epam.esm.consts.MessagesKeys;
import com.epam.esm.consts.NamedQueriesKeys;
import com.epam.esm.entity.Tag;
import com.epam.esm.repos.metadata.TableField;
import com.epam.esm.repos.query.TagSQL;
import exception.RepositoryException;
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
   * Creates new tag
   *
   * @param tag tag to create
   * @return id of the created tag
   * @throws RepositoryException when tag creation failed
   */
  public void create(Tag tag) {
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(tag);
      entityManager.getTransaction().commit();
    } catch (PersistenceException e) {
      entityManager.getTransaction().rollback();
      throw getExceptionForTagNameAlreadyExist(e, tag.getName());
    }
  }

  /**
   * Deletes tag by id <br>
   * If this tag is added to some certificate, it will be excluded from it without any exception
   *
   * @param id id of the tag to delete
   * @throws RepositoryException when tag with provided id is not found
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
   * Searches for tag by name
   *
   * @param name name of the tag to find
   * @return founded tag
   * @throws RepositoryException when tag with provided name is not found
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
   * Searches for tag by id
   *
   * @param id id of the tag to find
   * @throws RepositoryException when tag with provided id is not found
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

  /**
   * Searches for existing tags among provided by name
   *
   * @param tags tags to try to find
   * @return list of founded tags
   */
  public List<Tag> findExistingTags(Set<Tag> tags) {
    return entityManager
        .createNativeQuery(TagSQL.GET_QUERY_TO_FIND_EXISTING_TAGS(tags), Tag.class)
        .getResultList();
  }

  private RepositoryException getExceptionForTagNameAlreadyExist(
      PersistenceException e, String name) {
    logger.error("Attempt to create tag with name '" + name + "' got error: " + e.getCause());
    throw new RepositoryException(
        messageSource.getMessage(
            MessagesKeys.TAG_CREATION_FAILED,
            new Object[] {name},
            LocaleContextHolder.getLocale()));
  }

  private RepositoryException getExceptionForTagIdNotExist(NoResultException e, Long id) {
    logger.error("Tag {id=" + id + "} does not exist");
    throw new RepositoryException(
        messageSource.getMessage(
            MessagesKeys.TAG_ID_NOT_EXIST,
            new Object[] {id.toString()},
            LocaleContextHolder.getLocale()));
  }

  private RepositoryException getExceptionForTagNameNotExist(NoResultException e, String name) {
    logger.error("Tag {name='" + name + "'} does not exist");
    throw new RepositoryException(
        messageSource.getMessage(
            MessagesKeys.TAG_NAME_NOT_EXIST, new Object[] {name}, LocaleContextHolder.getLocale()),
        e);
  }
}
