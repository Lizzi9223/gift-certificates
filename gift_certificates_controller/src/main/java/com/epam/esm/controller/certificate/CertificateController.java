package com.epam.esm.controller.certificate;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.service.CertificateService;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
  private static final Logger logger = Logger.getLogger(CertificateController.class);
  private final CertificateService certificateService;

  @Autowired
  public CertificateController(CertificateService certificateService) {
    this.certificateService = certificateService;
  }

  /**
   * Creates new certificate
   *
   * @param certificateDto certificate to create
   */
  @PostMapping
  public void create(
      @RequestBody CertificateDto certificateDto) { // throws ResourceAlreadyExistExcepton
    certificateService.create(certificateDto);
  }

  /**
   * Updates existing certificate
   *
   * @param certificateDto info to update
   * @param id id of the certificate to update
   */
  @PutMapping(value = "/{id}")
  public void update(@RequestBody CertificateDto certificateDto, @PathVariable("id") int id) {
    certificateService.update(certificateDto, id);
  }

  /**
   * Deletes existing certificate
   *
   * @param id id of the certificate to delete
   */
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable("id") int id) {
    certificateService.delete(id);
  }

  /**
   * Searches for certificate by provided params
   *
   * @param tagName name of the tag that certificate should be connected with
   * @param name part of the certificate name
   * @param description part of the certificate description
   * @param sortByDate is sort by date should be carried out
   * @param sortByDateType sort by date ASC or DESC
   * @param sortByName is sort by name should be carried out
   * @param sortByNameType sort by name ASC or DESC
   * @return list of the certificates that correspond to the provided params
   */
//  @GetMapping
//  public List<CertificateDto> findByParams(
//      @RequestParam(required = false, name = "tagName") String tagName,
//      @RequestParam(required = false, name = "name") String name,
//      @RequestParam(required = false, name = "description") String description,
//      @RequestParam(required = false, name = "sortByDateType") String sortByDateType,
//      @RequestParam(required = false, name = "sortByNameType") String sortByNameType) {
//    return certificateService.find(tagName, name, description, sortByDateType, sortByNameType);
//  }

  @GetMapping
  public CertificateDto findByParams(
      @RequestParam(required = false, name = "name") String name) {
    return certificateService.find(name);
  }
}
