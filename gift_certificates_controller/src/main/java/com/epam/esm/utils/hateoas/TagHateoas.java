package com.epam.esm.utils.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Component;

/**
 * Contains methods for hateoas implementation for TagController
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class TagHateoas {
  /**
   * adds links to object
   *
   * @param tagDto object links added to
   */
  public void getSelfLink(TagDto tagDto) {
    tagDto.add(linkTo(methodOn(TagController.class).findByName(tagDto.getName())).withSelfRel());
    tagDto.add(linkTo(methodOn(TagController.class).delete(tagDto.getId())).withRel("delete"));
  }
}
