package com.epam.esm.controller.certificate;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.service.CertificateService;
import com.epam.esm.utils.Pagination;
import com.epam.esm.utils.hateoas.CertificateHateoas;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


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
  private final Pagination pagination;
  private final CertificateHateoas certificateHateoas;

  @Autowired
  public CertificateController(CertificateService certificateService, Pagination pagination, CertificateHateoas certificateHateoas) {
    this.certificateService = certificateService;
    this.pagination = pagination;
    this.certificateHateoas=certificateHateoas;
  }

  /**
   * Creates new certificate
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
   * @param certificateDto info to update
   * @param id id of the certificate to update
   * @return return ResponseEntity containing only http status (without body)
   */
  @PutMapping(value = "/{id}")
  public ResponseEntity<Void> update(@RequestBody CertificateDto certificateDto, @PathVariable("id") int id) {
    certificateService.update(certificateDto, id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Deletes existing certificate
   * @param id id of the certificate to delete
   * @return ResponseEntity containing only http status (without body)
   */
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") int id) {
    certificateService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Searches for certificate by provided params
   * @param tagNames name of the tag that certificate should be connected with
   * @param name part of the certificate name
   * @param description part of the certificate description
   * @param sortByDateType sort by date ASC or DESC
   * @param sortByNameType sort by name ASC or DESC
   * @return ResponseEntity containing http status and list of the certificates that correspond to the provided params
   */
  @GetMapping
  public ResponseEntity<List<CertificateDto>> findByName(
      @RequestParam(required = false, name = "tagNames") String[] tagNames,
      @RequestParam(required = false, name = "name") String name,
      @RequestParam(required = false, name = "description") String description,
      @RequestParam(required = false, name = "sortByDateType") String sortByDateType,
      @RequestParam(required = false, name = "sortByNameType") String sortByNameType,
      @RequestParam(required = true, name = "page") int page,
      @RequestParam(required = true, name = "pageSize") int pageSize) {
    List<CertificateDto> certificateDtos = (List<CertificateDto>)
        pagination.paginate(
            certificateService.find(tagNames, name, description, sortByDateType, sortByNameType),
            page,
            pageSize);
    certificateDtos.forEach(certificateHateoas::getLinks);
    return new ResponseEntity<>(certificateDtos, HttpStatus.OK);
  }

  /**
   * Searches for certificate with provided name
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
