package com.epam.esm.repos;

import com.epam.esm.consts.MessagesKeys;
import com.epam.esm.consts.NamedQueriesKeys;
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
public class CertificateRepository {
  private static final Logger logger = Logger.getLogger(CertificateRepository.class);
  public final EntityManager entityManager;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public CertificateRepository(
      EntityManager entityManager, ResourceBundleMessageSource messageSource) {
    this.entityManager = entityManager;
    this.messageSource = messageSource;
  }

  /**
   * Creates new certificate <br>
   * If certificate with provided name already exists, {@code ResourceAlreadyExistExcepton} is
   * thrown Otherwise created certificate id is returned
   *
   * @param certificate certificate to create
   */
  public void createOrUpdate(Certificate certificate) {
    if (Objects.nonNull(certificate.getId())) prepareCertificateForUpdate(certificate);
    try {
      entityManager.getTransaction().begin();
      entityManager.merge(certificate);
      entityManager.getTransaction().commit();
    } catch (PersistenceException e) {
      entityManager.getTransaction().rollback();
      throw getExceptionForCertificateNameAlreadyExist(e, certificate.getName());
    }
  }

  /**
   * Checks if certificate with provided id exists <br>
   * If it does not, {@code ResourceNotFound} exception is thrown <br>
   * Otherwise all fields of certificate are checked <br>
   * and if any field is not supposed to be updated, it is filled with initial data
   *
   * @param certificate certificate to update
   */
  private void prepareCertificateForUpdate(Certificate certificate) {
    Certificate initialCert = findById(certificate.getId());
    if (Objects.isNull(initialCert))
      throw getExceptionForCertificateIdNotExist(null, certificate.getId());
    if (Objects.isNull(certificate.getName())) certificate.setName(initialCert.getName());
    if (Objects.isNull(certificate.getDescription()))
      certificate.setDescription(initialCert.getDescription());
    if (Objects.isNull(certificate.getPrice())) certificate.setPrice(initialCert.getPrice());
    if (certificate.getDuration() == 0) certificate.setDuration(initialCert.getDuration());
    if (Objects.isNull(certificate.getCreateDate()))
      certificate.setCreateDate(initialCert.getCreateDate());
    if (Objects.isNull(certificate.getLastUpdateDate()))
      certificate.setLastUpdateDate(LocalDateTime.now());
  }

  /**
   * Deletes existing certificate<br>
   * If certificate with provided id does not exist, {@code ResourceNotFoundException} is thrown
   *
   * @param id id of the certificate to delete
   */
  public void delete(Long id) {
    Certificate certificate = entityManager.find(Certificate.class, id);
    if (Objects.nonNull(certificate)) {
      entityManager.getTransaction().begin();
      entityManager.remove(certificate);
      entityManager.getTransaction().commit();
    } else throw getExceptionForCertificateIdNotExist(null, id);
  }

  /**
   * Searches for certificate by provided name <br>
   * If certificate with provided name does not exist, {@code ResourceNotFoundException} is thrown
   *
   * @param name name of the certificate to search for
   * @return founded certificate
   */
  public Certificate find(String name) {
    try {
      return entityManager
          .createNamedQuery(NamedQueriesKeys.CERTIFICATE_FIND_BY_NAME, Certificate.class)
          .setParameter(TableField.NAME, name)
          .getSingleResult();
    } catch (NoResultException e) {
      throw getExceptionForCertificateNameNotExist(e, name);
    }
  }

  /**
   * Searches for certificate by provided id <br>
   * If certificate with provided id does not exist, {@code ResourceNotFoundException} is thrown
   *
   * @param id id of the certificate to search for
   * @return founded certificate
   */
  public Certificate findById(Long id) {
    Certificate certificate = entityManager.find(Certificate.class, id);
    if (Objects.nonNull(certificate)) return certificate;
    else throw getExceptionForCertificateIdNotExist(null, id);
  }

  /**
   * Searches for certificates by provided params
   *
   * @param searchCriteria contains params to search for certificates by
   * @return list of founded certificates
   */
  public List<Certificate> find(SearchCriteria searchCriteria) {
    if (SearchCriteriaValidator.isValid(searchCriteria)) {
      return entityManager
          .createNativeQuery(CertificateSQL.GET_FIND_QUERY(searchCriteria), Certificate.class)
          .getResultList();
    } else throw getInvalidSearchParamsException();
  }

  private ResourceNotFoundException getExceptionForCertificateIdNotExist(
      NoResultException e, Long id) {
    logger.error("Certificate {id='" + id + "'} does not exist");
    return new ResourceNotFoundException(
        messageSource.getMessage(
            MessagesKeys.CERTIFICATE_ID_NOT_EXIST,
            new Object[] {id},
            LocaleContextHolder.getLocale()),
        e);
  }

  private ResourceNotFoundException getExceptionForCertificateNameNotExist(
      NoResultException e, String name) {
    logger.error("Certificate {name='" + name + "'} does not exist");
    return new ResourceNotFoundException(
        messageSource.getMessage(
            MessagesKeys.CERTIFICATE_NAME_NOT_EXIST,
            new Object[] {name},
            LocaleContextHolder.getLocale()),
        e);
  }

  private ResourceAlreadyExistExcepton getExceptionForCertificateNameAlreadyExist(
      PersistenceException e, String name) {
    logger.error("Certificate {name='" + name + "'} already exists");
    return new ResourceAlreadyExistExcepton(
        messageSource.getMessage(
            MessagesKeys.CERTIFICATE_NAME_ALREADY_EXIST,
            new Object[] {name},
            LocaleContextHolder.getLocale()),
        e);
  }

  private InvalidSearchParamsException getInvalidSearchParamsException() {
    logger.error("Search certificates parameters are invalid");
    return new InvalidSearchParamsException(
        messageSource.getMessage(
            MessagesKeys.INVALID_SEARCH_PARAMS, null, LocaleContextHolder.getLocale()));
  }
}
