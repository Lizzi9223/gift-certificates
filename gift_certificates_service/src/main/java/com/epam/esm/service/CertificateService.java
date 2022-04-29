package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.mappers.CertificateMapper;
import com.epam.esm.mappers.TagMapper;
import com.epam.esm.repos.CertificateRepository;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.group.CreateInfo;
import com.epam.esm.validator.group.UpdateInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
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
  private static final Logger logger = Logger.getLogger(CertificateService.class);
  private final CertificateRepository certificateRepository;
  private TagService tagService;

  //private final CertificateTagsRepository certificateTagsRepository;
  private CertificateMapper certificateMapper;
  private TagMapper tagMapper;
  private final DtoValidator dtoValidator;

  @Autowired
  public CertificateService(
      CertificateRepository certificateRepository,
      //CertificateTagsRepository certificateTagsRepository,
      TagService tagService,
      CertificateMapper certificateMapper,
      TagMapper tagMapper,
      DtoValidator dtoValidator) {
    this.certificateRepository = certificateRepository;
    //this.certificateTagsRepository = certificateTagsRepository;
    this.tagService = tagService;
    this.certificateMapper = certificateMapper;
    this.tagMapper = tagMapper;
    this.dtoValidator = dtoValidator;
  }

  /**
   * Creates new certificate Validation is provided <br>
   * If certificateDto containt new tags, they will be created as well
   *
   * @param certificateDto certificate to create
   */
  //@Transactional(rollbackFor = Exception.class)
  public void create(CertificateDto certificateDto)  { //throws ResourceAlreadyExistExcepton
    dtoValidator.validate(certificateDto, CreateInfo.class);
//    Certificate certificate = certificateMapper.convertToEntity(certificateDto);
//    int certificateId = certificateRepository.create(certificate);
//    if (Objects.nonNull(certificateDto.getTags())) {
//      int[] tagIds = tagService.create(certificateDto.getTags());
//      certificateTagsRepository.create(certificateId, tagIds);
//    }
  }

  /**
   * Updates existing certificate Validation is provided <br>
   * If certificateDto containt new tags, they will be created as well
   *
   * @param certificateDto certificate info for update
   * @param id id of the certificate to update
   */
  //@Transactional(rollbackFor = Exception.class)
  public void update(CertificateDto certificateDto, int id) {
    dtoValidator.validate(certificateDto, UpdateInfo.class);
    certificateDto.setId(id);
//    Certificate certificate = certificateMapper.convertToEntity(certificateDto);
//    certificateRepository.update(certificate);
//    if (Objects.nonNull(certificateDto.getTags())) {
//      int[] tagIds = tagService.create(certificateDto.getTags());
//      certificateTagsRepository.create(id, tagIds);
//    }
  }

  /**
   * Deletes certificate
   *
   * @param id id of the certificate to delete
   */
  public void delete(int id) {
    //certificateRepository.delete(id);
  }

  /**
   * Searches for certificates by params
   *
   * @param tagName name of the tag certificates to contain
   * @param certificateName part of certificate name
   * @param certificateDescription part of certificate description
   * @param sortByDate is sorting by date should be provided
   * @param sortByDateType sort by date ASC or DESC
   * @param sortByName is sorting by name should be provided
   * @param sortByNameType sort by name ASC or DESC
   * @return list of founded certificateDtos
   */
  public List<CertificateDto> find(
      String tagName,
      String certificateName,
      String certificateDescription,
      String sortByDateType,
      String sortByNameType) {
//    SearchCriteria searchCriteria =
//        new SearchCriteria(
//            tagName,
//            certificateName,
//            certificateDescription,
//            sortByDateType,
//            sortByNameType);
//    List<Certificate> certificates = certificateRepository.find(searchCriteria);
//    List<CertificateDto> certificateDtos = certificateMapper.convertToDto(certificates);
//    certificateDtos.forEach(
//        c ->
//            c.setTags(
//                tagMapper.convertToDto(certificateTagsRepository.findCertificateTags(c.getId()))));
//    return certificateDtos;
    return new ArrayList<>();
  }

  /**
   * Searches for certificate by name
   *
   * @param name name of the certificate to find
   * @return founded certificateDto
   */
//  public CertificateDto find(String name) {
//    Optional<Certificate> certificate = certificateRepository.find(name);
//    if (certificate.isPresent()) {
//      CertificateDto certificateDto = certificateMapper.convertToDto(certificate.get());
//      //List<Tag> tags = certificateTagsRepository.findCertificateTags(certificateDto.getId());
//      //certificateDto.setTags(tagMapper.convertToDto(tags));
//      return certificateDto;
//    } else return null;
//    return null;
//  }

  public CertificateDto find(String name) {
    Optional<Certificate> certificate = certificateRepository.find(name);
    if (certificate.isPresent()) {
      return certificateMapper.convertToDto(certificate.get());
    } else return null;
  }
}
