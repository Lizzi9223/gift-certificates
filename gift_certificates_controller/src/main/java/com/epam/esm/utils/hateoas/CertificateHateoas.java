package com.epam.esm.utils.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.certificate.CertificateController;
import com.epam.esm.dto.CertificateDto;
import org.springframework.stereotype.Component;

/**
 * Contains methods for hateoas implementation for CertificateController
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class CertificateHateoas {

  /**
   * adds links to object
   *
   * @param certificateDto object links added to
   */
  public void getLinks(CertificateDto certificateDto) {
    certificateDto.add(
        linkTo(methodOn(CertificateController.class).findByName(certificateDto.getName()))
            .withSelfRel());
    certificateDto.add(
        linkTo(methodOn(CertificateController.class).update(certificateDto, certificateDto.getId()))
            .withRel("update"));
    certificateDto.add(
        linkTo(methodOn(CertificateController.class).delete(certificateDto.getId()))
            .withRel("delete"));
  }
}
