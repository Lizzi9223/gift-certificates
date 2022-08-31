package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.handling.CertificateExceptions;
import com.epam.esm.mappers.CertificateMapper;
import com.epam.esm.repos.CertificateRepository;
import com.epam.esm.repos.TagRepository;
import com.epam.esm.search.model.SearchCriteria;
import com.epam.esm.search.validator.SearchCriteriaValidator;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.group.CreateInfo;
import com.epam.esm.validator.group.UpdateInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Service layer for certificate operations
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Service
public class CertificateService {
  private final CertificateExceptions exceptionHandling;
  private final CertificateRepository certificateRepository;
  private final TagRepository tagRepository;
  private final CertificateMapper certificateMapper;
  private final DtoValidator dtoValidator;

  @Autowired
  public CertificateService(
      CertificateExceptions exceptionHandling,
      CertificateRepository certificateRepository,
      TagRepository tagRepository,
      CertificateMapper certificateMapper,
      DtoValidator dtoValidator) {
    this.exceptionHandling = exceptionHandling;
    this.certificateRepository = certificateRepository;
    this.tagRepository = tagRepository;
    this.certificateMapper = certificateMapper;
    this.dtoValidator = dtoValidator;
  }

  /**
   * Creates new certificate <br>
   * Validation is provided <br>
   * If certificateDto contains new tags, they will be created as well
   *
   * @param certificateDto certificate to create
   */
  public void create(CertificateDto certificateDto) {
    dtoValidator.validate(certificateDto, CreateInfo.class);
    if (Objects.isNull(certificateDto.getCreateDate())) {
      certificateDto.setCreateDate(LocalDateTime.now());
    }
    if (Objects.isNull(certificateDto.getLastUpdateDate())) {
      certificateDto.setLastUpdateDate(LocalDateTime.now());
    }
    createOrUpdate(certificateMapper.convertToEntity(certificateDto));
  }

  /**
   * Updates existing certificate <br>
   * Validation is provided <br>
   * If certificateDto containt new tags, they will be created as well
   *
   * @param certificateDto certificate info for update
   * @param id id of the certificate to update
   */
  public void update(CertificateDto certificateDto, Long id) {
    dtoValidator.validate(certificateDto, UpdateInfo.class);
    certificateDto.setId(id);
    Certificate certificate = certificateMapper.convertToEntity(certificateDto);
    prepareCertificateForUpdate(certificate);
    createOrUpdate(certificate);
  }

  /**
   * Checks if certificate with provided id exists <br>
   * If it does not, {@code ServiceException} exception is thrown <br>
   * Otherwise all fields of certificate are checked <br>
   * and if any field is not supposed to be updated, it is filled with initial data
   *
   * @param certificate certificate to update
   * @throws ServiceException when certificate with provided id is not found
   */
  private void prepareCertificateForUpdate(Certificate certificate) {
    Optional<Certificate> initialCert = certificateRepository.findById(certificate.getId());
    if (initialCert.isEmpty())
      throw exceptionHandling.getExceptionForCertificateIdNotExist(certificate.getId());
    if (Objects.isNull(certificate.getName())) certificate.setName(initialCert.get().getName());
    if (Objects.isNull(certificate.getDescription()))
      certificate.setDescription(initialCert.get().getDescription());
    if (Objects.isNull(certificate.getPrice())) certificate.setPrice(initialCert.get().getPrice());
    if (certificate.getDuration() == 0) certificate.setDuration(initialCert.get().getDuration());
    if (Objects.isNull(certificate.getCreateDate()))
      certificate.setCreateDate(initialCert.get().getCreateDate());
    if (Objects.isNull(certificate.getLastUpdateDate()))
      certificate.setLastUpdateDate(LocalDateTime.now());
    if (Objects.nonNull(initialCert.get().getTags())) {
      initialCert.get().getTags().forEach(tag -> certificate.getTags().add(tag));
    }
  }

  /**
   * Creates/updates certificate
   *
   * @param certificate certificate to create/update
   * @throws ServiceException if certificate with the provided name already exists
   */
  private void createOrUpdate(Certificate certificate) {
    if (Objects.nonNull(certificate.getTags())) {
      setIdsToTagsIfExists(certificate.getTags());
    }
    try {
      certificateRepository.save(certificate);
    } catch (DataIntegrityViolationException e) {
      throw exceptionHandling.getExceptionForCertificateNameAlreadyExist(e, certificate.getName());
    }
  }

  /**
   * Finds existing tags among the provided ones<br>
   * and replace them on their entities
   *
   * @param tagsToFind set of tags to find
   */
  private void setIdsToTagsIfExists(Set<Tag> tagsToFind) {
    List<Tag> tags = tagRepository.findExistingTags(tagsToFind);
    tags.forEach(tagsToFind::remove);
    tagsToFind.addAll(tags);
  }

  /**
   * Deletes certificate
   *
   * @param id id of the certificate to delete
   * @throws ServiceException if certificate with the provided id does not exist
   */
  public void delete(Long id) {
    Optional<Certificate> certificate = certificateRepository.findById(id);
    if (certificate.isPresent()) certificateRepository.delete(certificate.get());
    else throw exceptionHandling.getExceptionForCertificateIdNotExist(id);
  }

  /**
   * Searches for certificates by params
   *
   * @param tagNames name of the tag certificates to contain
   * @param certificateName part of certificate name
   * @param certificateDescription part of certificate description
   * @param sortByDateType sort by date ASC or DESC
   * @param sortByNameType sort by name ASC or DESC
   * @return list of founded certificateDtos
   */
  public List<CertificateDto> find(
      String[] tagNames,
      String certificateName,
      String certificateDescription,
      String sortByDateType,
      String sortByNameType) {
    SearchCriteria criteria =
        new SearchCriteria(
            tagNames, certificateName, certificateDescription, sortByDateType, sortByNameType);
    if (SearchCriteriaValidator.isValid(criteria)) {
      List<Certificate> certificates = certificateRepository.findByCriteria(criteria);
      return certificateMapper.convertToDto(certificates);
    } else throw exceptionHandling.getInvalidSearchParamsException();
  }

  /**
   * Searches for certificate by name
   *
   * @param name name of the certificate to find
   * @return founded certificateDto
   * @throws ServiceException if certificate with the provided name does not exist
   */
  public CertificateDto find(String name) {
    Optional<Certificate> certificate = certificateRepository.findByName(name);
    if (certificate.isPresent()) return certificateMapper.convertToDto(certificate.get());
    else throw exceptionHandling.getExceptionForCertificateNameNotExist(name);
  }
}
