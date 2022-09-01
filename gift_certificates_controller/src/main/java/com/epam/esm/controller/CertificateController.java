package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.service.CertificateService;
import com.epam.esm.utils.hateoas.CertificateHateoas;
import java.security.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests to /certificate url (contains CRUD operations with certificates)
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {
  private final CertificateService certificateService;
  private final CertificateHateoas certificateHateoas;

  @Autowired
  public CertificateController(
      CertificateService certificateService, CertificateHateoas certificateHateoas) {
    this.certificateService = certificateService;
    this.certificateHateoas = certificateHateoas;
  }

  /**
   * Creates new certificate
   *
   * @param certificateDto certificate to create
   * @return return ResponseEntity containing only http status (without body)
   */
  @PostMapping
  public ResponseEntity<Void> create(@RequestBody CertificateDto certificateDto) {
    certificateService.create(certificateDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Updates existing certificate
   *
   * @param certificateDto info to update
   * @param id id of the certificate to update
   * @return return ResponseEntity containing only http status (without body)
   */
  @PutMapping(value = "/{id}")
  public ResponseEntity<Void> update(
      @RequestBody CertificateDto certificateDto, @PathVariable("id") Long id) {
    certificateService.update(certificateDto, id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Deletes existing certificate
   *
   * @param id id of the certificate to delete
   * @return ResponseEntity containing only http status (without body)
   */
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    certificateService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Searches for certificate by provided params
   *
   * @param tagNames names of the tags that certificates has to be attached to
   * @param name substring that certificate has to contain in its name or description
   * @param pageable for pagination implementation
   * @return ResponseEntity containing http status and list of the certificates that correspond to
   *     the provided params
   */
  @GetMapping
  public ResponseEntity<Page<CertificateDto>> findByName(
      @RequestParam(required = false, name = "tagNames") String[] tagNames,
      @RequestParam(required = false, name = "name") String name,
      Pageable pageable) {
    if (pageable.getPageSize() < 0 || pageable.getPageNumber() < 0)
      throw new InvalidParameterException(
          "Number of page or page size in pageable must be integer numbers and cannot be less than 1");
    Page<CertificateDto> certificateDtos =
        certificateService.find(tagNames, name, pageable);
    certificateDtos.forEach(certificateHateoas::getLinks);
    return new ResponseEntity<>(certificateDtos, HttpStatus.OK);
  }

  /**
   * Searches for certificate with provided name
   *
   * @param name name of the certificate to find
   * @return ResponseEntity containing http status and founded certificate
   */
  @GetMapping(value = "/{name}")
  public ResponseEntity<CertificateDto> findByName(@PathVariable("name") String name) {
    CertificateDto certificateDto = certificateService.find(name);
    certificateHateoas.getLinks(certificateDto);
    return new ResponseEntity<>(certificateDto, HttpStatus.OK);
  }
}
