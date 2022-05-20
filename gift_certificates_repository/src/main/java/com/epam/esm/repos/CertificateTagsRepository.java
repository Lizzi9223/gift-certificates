package com.epam.esm.repos;

import com.epam.esm.entity.CertificateTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.key.CertificateTagId;
import com.epam.esm.exception.ResourceAlreadyExistExcepton;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repos.metadata.TableField;
import com.epam.esm.repos.query.CertificateTagSQL;
import com.epam.esm.repos.query.TagSQL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
public class CertificateTagsRepository {
  private static final Logger logger = Logger.getLogger(CertificateRepository.class);
  private final EntityManagerFactory entityManagerFactory;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public CertificateTagsRepository(
      EntityManagerFactory entityManagerFactory, ResourceBundleMessageSource messageSource) {
    this.entityManagerFactory = entityManagerFactory;
    this.messageSource = messageSource;
  }

  /**
   * Adds tag to certificate
   *
   * @param certificateId certificate's id to add tag to
   * @param tagId id of tag to add to the certificate
   */
  public void create(CertificateTag certificateTag){
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(certificateTag);
      entityManager.getTransaction().commit();
      entityManager.close();
    } catch (PersistenceException e) {
      entityManager.close();
      logger.error(
          "Record {certificateId=" + certificateTag.getCertificateTagId().getCertificateId()
              + ",tagId=" + certificateTag.getCertificateTagId().getTagId() + "} already exists");
      throw new ResourceAlreadyExistExcepton(
          messageSource.getMessage(
              "message.repository.recordExists",
              new Object[] {certificateTag.getCertificateTagId().getCertificateId(), certificateTag.getCertificateTagId().getTagId()},
              LocaleContextHolder.getLocale()),
          e);
    }
  }

  /**
   * Adds tags to certificate
   *
   * @param certificateId certificate's id to add tags to
   * @param tagIds id of tags to add to the certificate
   */
  public void create(int certificateId, int[] tagIds) {
    Arrays.stream(tagIds)
        .forEach(
            tagId -> {
              try {
                create(new CertificateTag(new CertificateTagId(certificateId, tagId)));
              } catch (ResourceAlreadyExistExcepton e) {
                logger.warn(
                    "Record {certificateId="
                        + certificateId
                        + ",tagId="
                        + tagId
                        + "} already exists");
              }
            });
  }

  /**
   * Excludes certain tag from belonging to certain certificate
   *
   * @param certificateId id of the certificate that tag to exclude
   * @param tagId id of the tag to exclude from the certificate
   */
  public void delete(CertificateTagId certificateTagId) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    CertificateTag certificateTag = entityManager.find(CertificateTag.class, certificateTagId);
    if (Objects.nonNull(certificateTag)) {
      entityManager.getTransaction().begin();
      entityManager.remove(certificateTag);
      entityManager.getTransaction().commit();
      entityManager.close();
    } else {
      entityManager.close();
      logger.error(
          "Certificate {id=" + certificateTagId.getCertificateId() + "}  does not contain tag {id=" + certificateTagId.getTagId() + "}");
      throw new ResourceNotFoundException(
          messageSource.getMessage(
              "message.repository.recordNotExists",
              new Object[] {certificateTagId.getCertificateId(), certificateTagId.getTagId()},
              LocaleContextHolder.getLocale()));
    }
  }

  /**
   * Searches for all tags that belong to certificate with provided id
   *
   * @param id is of the certificate that tags to find
   * @return list of founded tags
   */
  public List<Tag> findCertificateTags(int id) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    List<Tag> tags =
        entityManager
            .createNativeQuery(CertificateTagSQL.FIND_CERTIFICATE_TAGS, Tag.class)
            .setParameter(TableField.ID, id)
            .getResultList();
    entityManager.close();
    return tags;
  }
}
