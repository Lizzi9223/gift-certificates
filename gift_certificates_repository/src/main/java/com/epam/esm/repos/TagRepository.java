package com.epam.esm.repos;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceAlreadyExistExcepton;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repos.metadata.TableField;
import com.epam.esm.repos.query.TagSQL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
  private final EntityManagerFactory entityManagerFactory;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public TagRepository(
      EntityManagerFactory entityManagerFactory, ResourceBundleMessageSource messageSource) {
    this.entityManagerFactory = entityManagerFactory;
    this.messageSource = messageSource;
  }

  /**
   * Creates new tag <br>
   * If tag with provided name already exists, {@code ResourceAlreadyExistExcepton} is thrown
   *
   * @param tag tag to create
   * @return id of the created tag
   */
  public int create(Tag tag) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(tag);
      entityManager.getTransaction().commit();
      entityManager.close();
      return tag.getId();
    } catch (PersistenceException e) {
      entityManager.close();
      logger.error("Tag {name='" + tag.getName() + "'} already exists");
      throw new ResourceAlreadyExistExcepton(
          messageSource.getMessage(
              "message.repository.tagNameExists",
              new Object[] {tag.getName()},
              LocaleContextHolder.getLocale()));
    }
  }

  /**
   * Create new tags
   *
   * @param tags list of the tags to create
   * @return ids of the created tags
   */
  public int[] create(List<Tag> tags) {
    int[] ids = new int[tags.size()];
    IntStream.range(0, tags.size())
        .forEach(
            index -> {
              try {
                ids[index] = create(tags.get(index));
              } catch (ResourceAlreadyExistExcepton e) {
                ids[index] = find(tags.get(index).getName()).get().getId();
                logger.warn("Tag {name='" + tags.get(index).getName() + "'} already exists");
              }
            });
    return ids;
  }

  /**
   * Deletes tag by id <br>
   * If this tag is added to some certificate, it will be excluded from it with no exception
   *
   * @param id id of the tag to delete
   */
  public void delete(int id) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Tag tag = entityManager.find(Tag.class, id);
    if (Objects.nonNull(tag)) {
      entityManager.getTransaction().begin();
      entityManager.remove(tag);
      entityManager.getTransaction().commit();
      entityManager.close();
    } else {
      entityManager.close();
      logger.error("Tag {id=" + id + "} does not exist");
      throw new ResourceNotFoundException(
          messageSource.getMessage(
              "message.repository.tagIdNotExists",
              new Object[] {id},
              LocaleContextHolder.getLocale()));
    }
  }

  /**
   * Searches for tag by name <br>
   * Throws {@code EmptyResultDataAccessException} if tag with provided name does not exist
   *
   * @param name name of the tag to find
   * @return founded tag
   */
  public Optional<Tag> find(String name) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try {
      Optional<Tag> tag =
          Optional.of(
              entityManager
                  .createQuery(TagSQL.FIND_BY_NAME, Tag.class)
                  .setParameter(TableField.NAME, name)
                  .getSingleResult());
      entityManager.close();
      return tag;
    } catch (NoResultException e) {
      entityManager.close();
      logger.error("Tag {name='" + name + "'} does not exist");
      throw new ResourceNotFoundException(
          messageSource.getMessage(
              "message.repository.tagNameNotExists",
              new Object[] {name},
              LocaleContextHolder.getLocale()));
    }
  }

  /**
   * Searches for all tags
   *
   * @return list of all existing tags
   */
  public List<Tag> findAll() {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    List<Tag> tags = entityManager.createQuery(TagSQL.FIND_ALL, Tag.class).getResultList();
    entityManager.close();
    return tags;
  }
}
