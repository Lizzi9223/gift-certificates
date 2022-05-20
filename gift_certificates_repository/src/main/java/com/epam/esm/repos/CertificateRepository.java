package com.epam.esm.repos;

import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.InvalidSearchParamsException;
import com.epam.esm.exception.ResourceAlreadyExistExcepton;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repos.metadata.TableField;
import com.epam.esm.repos.query.CertificateSQL;
import com.epam.esm.search.model.SearchCriteria;
import com.epam.esm.search.validator.SearchCriteriaValidator;
import java.time.LocalDateTime;
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
public class CertificateRepository {
  private static final Logger logger = Logger.getLogger(CertificateRepository.class);
  private final EntityManagerFactory entityManagerFactory;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public CertificateRepository(
      EntityManagerFactory entityManagerFactory, ResourceBundleMessageSource messageSource) {
    this.entityManagerFactory = entityManagerFactory;
    this.messageSource = messageSource;
  }

  /**
   * Creates new certificate <br>
   * If certificate with provided name already exists, {@code ResourceAlreadyExistExcepton} is
   * thrown Otherwise created certificate id is returned
   *
   * @param certificate certificate to create
   * @return created certificate id
   */
  public int create(Certificate certificate) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try {
      if (Objects.isNull(certificate.getCreateDate()))
        certificate.setCreateDate(LocalDateTime.now());
      if (Objects.isNull(certificate.getLastUpdateDate()))
        certificate.setLastUpdateDate(LocalDateTime.now());
      entityManager.getTransaction().begin();
      entityManager.persist(certificate);
      entityManager.getTransaction().commit();
      entityManager.close();
      return certificate.getId();
    } catch (PersistenceException e) {
      entityManager.close();
      logger.error("Certificate {name='" + certificate.getName() + "'} already exists");
      throw new ResourceAlreadyExistExcepton(
          messageSource.getMessage(
              "message.repository.certificateNameExists",
              new Object[] {certificate.getName()},
              LocaleContextHolder.getLocale()),
          e);
    }
  }

  /**
   * Updates existing certificate <br>
   * If certificate with provided id does not exist, {@code ResourceNotFoundException} is thrown
   *
   * @param certificateNew info for update
   */
  public void update(Certificate certificateNew) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Certificate certificate = entityManager.find(Certificate.class, certificateNew.getId());
    if (Objects.nonNull(certificate)) {
      try {
        entityManager.getTransaction().begin();
        getCertificateToUpdate(certificate, certificateNew);
        certificate.setLastUpdateDate(LocalDateTime.now());
        entityManager.getTransaction().commit();
        entityManager.close();
      } catch (PersistenceException e) {
        entityManager.close();
        logger.error("Certificate {name='" + certificate.getName() + "'} already exists");
        throw new ResourceAlreadyExistExcepton(
            messageSource.getMessage(
                "message.repository.certificateNameExists",
                new Object[] {certificate.getName()},
                LocaleContextHolder.getLocale()),
            e);
      }
    } else {
      entityManager.close();
      logger.error("Certificate {id=" + certificateNew.getId() + "} does not exist");
      throw new ResourceNotFoundException(
          messageSource.getMessage(
              "message.repository.certificateIdNotExists",
              new Object[] {certificateNew.getId()},
              LocaleContextHolder.getLocale()));
    }
  }

  /**
   * Deletes existing certificate<br>
   * If certificate with provided id does not exist, {@code ResourceNotFoundException} is thrown
   *
   * @param id id of the certificate to delete
   */
  public void delete(int id) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    Certificate certificate = entityManager.find(Certificate.class, id);
    if (Objects.nonNull(certificate)) {
      entityManager.getTransaction().begin();
      entityManager.remove(certificate);
      entityManager.getTransaction().commit();
      entityManager.close();
    } else {
      entityManager.close();
      logger.error("Certificate {id=" + id + "} does not exist");
      throw new ResourceNotFoundException(
          messageSource.getMessage(
              "message.repository.certificateIdNotExists",
              new Object[] {id},
              LocaleContextHolder.getLocale()));
    }
  }

  /**
   * Searches for certificate with provided name <br>
   * If certificate with provided name does not exist, {@code ResourceNotFoundException} is thrown
   *
   * @param name name of the certificate to search for
   * @return founded certificate
   */
  public Optional<Certificate> find(String name) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try {
      Optional<Certificate> certificate =
          Optional.of(
              entityManager
                  .createQuery(CertificateSQL.FIND_BY_NAME, Certificate.class)
                  .setParameter(TableField.NAME, name)
                  .getSingleResult());
      entityManager.close();
      return certificate;
    } catch (NoResultException e) {
      entityManager.close();
      logger.error("Certificate {name='" + name + "'} does not exist");
      throw new ResourceNotFoundException(
          messageSource.getMessage(
              "message.repository.certificateNameNotExists",
              new Object[] {name},
              LocaleContextHolder.getLocale()),
          e);
    }
  }

  /**
   * Searches for certificates by provided params
   *
   * @param searchCriteria contains params to search for certificates by
   * @return list of founded certificates
   */
  public List<Certificate> find(SearchCriteria searchCriteria) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    if (SearchCriteriaValidator.isValid(searchCriteria)) {
      List<Certificate> certificates =
          entityManager
              .createNativeQuery(CertificateSQL.GET_FIND_QUERY(searchCriteria), Certificate.class)
              .getResultList();
      entityManager.close();
      return certificates;
    } else {
      entityManager.close();
      logger.error("Search certificates parameters are invalid");
      throw new InvalidSearchParamsException(
          messageSource.getMessage(
              "message.repository.invalidSearchParams", null, LocaleContextHolder.getLocale()));
    }
  }

  private Certificate getCertificateToUpdate(Certificate certInitial, Certificate certNew) {
    if (Objects.nonNull(certNew.getName())) certInitial.setName(certNew.getName());
    if (Objects.nonNull(certNew.getDescription()))
      certInitial.setDescription(certNew.getDescription());
    if (Objects.nonNull(certNew.getPrice())) certInitial.setPrice(certNew.getPrice());
    if (Objects.nonNull(certNew.getCreateDate()))
      certInitial.setCreateDate(certNew.getCreateDate());
    if (certNew.getDuration() != 0) certInitial.setDuration(certNew.getDuration());
    return certInitial;
  }
}
