package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.MostWidelyUsedTagService;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.hateoas.TagHateoas;
import java.security.InvalidParameterException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests to /tag url (contains CRD operations with tags)
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/tag")
public class TagController {
  private final TagService tagService;
  private final MostWidelyUsedTagService mostWidelyUsedTagService;
  private final TagHateoas tagHateoas;

  @Autowired
  public TagController(
      TagService tagService,
      MostWidelyUsedTagService mostWidelyUsedTagService,
      TagHateoas tagHateoas) {
    this.tagService = tagService;
    this.mostWidelyUsedTagService = mostWidelyUsedTagService;
    this.tagHateoas = tagHateoas;
  }

  /**
   * Creates new tag
   *
   * @param tagDto tag to create
   * @return return ResponseEntity containing only http status (without body)
   */
  @PostMapping
  public ResponseEntity<Void> create(@RequestBody TagDto tagDto) {
    tagService.create(tagDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Deletes existing tag
   *
   * @param id id of the tag to delete
   * @return return ResponseEntity containing only http status (without body)
   */
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    tagService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Searches for all existing tags tags
   *
   * @param pageable for pagination implementation
   * @return ResponseEntity containing http status and list with founded tags
   */
  @GetMapping
  public ResponseEntity<List<TagDto>> findAll(Pageable pageable) {
    if (pageable.getPageSize() < 0 || pageable.getPageNumber() < 0)
      throw new InvalidParameterException(
          "Number of page or page size in pageable must be integer numbers and cannot be less than 1");
    List<TagDto> tagDtos = tagService.findAll(pageable);
    tagDtos.forEach(tagHateoas::getSelfLink);
    return new ResponseEntity<>(tagDtos, HttpStatus.OK);
  }

  /**
   * Searches for tag by name
   *
   * @param name name of the tag to find
   * @return ResponseEntity containing http status and founded tag
   */
  @GetMapping(value = "/{name}")
  public ResponseEntity<TagDto> findByName(@PathVariable("name") String name) {
    TagDto tagDto = tagService.findByName(name);
    tagHateoas.getSelfLink(tagDto);
    return new ResponseEntity<>(tagDto, HttpStatus.OK);
  }

  /**
   * Searches for the most widely used tag of a user with the highest cost of all orders *
   *
   * @return ResponseEntity containing http status and founded tag
   */
  @GetMapping(value = "/find")
  public ResponseEntity<TagDto> findMostWidelyUsedTag() {
    TagDto tagDto = mostWidelyUsedTagService.findTag();
    tagHateoas.getSelfLink(tagDto);
    return new ResponseEntity<>(tagDto, HttpStatus.OK);
  }
}
