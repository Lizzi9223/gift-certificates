package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mappers.CertificateMapper;
import com.epam.esm.repos.CertificateRepository;
import com.epam.esm.repos.TagRepository;
import com.epam.esm.search.model.SearchCriteria;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.group.CreateInfo;
import com.epam.esm.validator.group.UpdateInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer for certificate operations
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Service
public class CertificateService {
  private final CertificateRepository certificateRepository;
  private final TagRepository tagRepository;
  private final CertificateMapper certificateMapper;
  private final DtoValidator dtoValidator;

  @Autowired
  public CertificateService(
      CertificateRepository certificateRepository,
      TagRepository tagRepository,
      CertificateMapper certificateMapper,
      DtoValidator dtoValidator) {
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
    createOrUpdate(certificateDto);
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
    createOrUpdate(certificateDto);
  }

  private void createOrUpdate(CertificateDto certificateDto) {
    Certificate certificate = certificateMapper.convertToEntity(certificateDto);
    setIdsToTagsIfExists(certificate.getTags());
    certificateRepository.createOrUpdate(certificate);
  }

  /**
   * If tag from collection exists, id of the tag will be set <br>
   * to the corresponding object in collection <br>
   * If this not done, exception 'Duplicate entry' will occur <br>
   * because Hibernate will try to insert these entities <br>
   * (but this way Hibernate will update them and that's fine)
   *
   * @param tagsToFind set of tags to find
   */
  private void setIdsToTagsIfExists(Set<Tag> tagsToFind) {
    List<Tag> tags = tagRepository.findExistingTags(tagsToFind);
    tagsToFind.forEach(
        tagToFind -> {
          tags.forEach(
              existingTag -> {
                if (existingTag.equals(tagToFind)) tagToFind.setId(existingTag.getId());
              });
        });
  }

  /**
   * Deletes certificate
   *
   * @param id id of the certificate to delete
   */
  public void delete(Long id) {
    certificateRepository.delete(id);
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
    SearchCriteria searchCriteria =
        new SearchCriteria(
            tagNames, certificateName, certificateDescription, sortByDateType, sortByNameType);
    List<Certificate> certificates = certificateRepository.find(searchCriteria);
    return certificateMapper.convertToDto(certificates);
  }

  /**
   * Searches for certificate by name
   *
   * @param name name of the certificate to find
   * @return founded certificateDto
   */
  public CertificateDto find(String name) {
    Certificate certificate = certificateRepository.find(name);
    return certificateMapper.convertToDto(certificate);
  }
}
