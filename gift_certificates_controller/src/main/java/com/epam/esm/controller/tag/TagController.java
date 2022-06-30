package com.epam.esm.controller.tag;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.MostWidelyUsedTagService;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.Pagination;
import com.epam.esm.utils.hateoas.TagHateoas;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  private static final Logger logger = Logger.getLogger(TagController.class);
  private final TagService tagService;
  private final MostWidelyUsedTagService mostWidelyUsedTagService;
  private final Pagination pagination;
  private final TagHateoas tagHateoas;

  @Autowired
  public TagController(
      TagService tagService,
      MostWidelyUsedTagService mostWidelyUsedTagService,
      Pagination pagination,
      TagHateoas tagHateoas) {
    this.tagService = tagService;
    this.mostWidelyUsedTagService = mostWidelyUsedTagService;
    this.pagination = pagination;
    this.tagHateoas=tagHateoas;
  }

  /**
   * Creates new tag
   * @param tagDto tag to create
   * @return return ResponseEntity containing only http status (without body)
   */
  @PostMapping
  public ResponseEntity<Void> create(@RequestBody TagDto tagDto) { // throws ResourceAlreadyExistExcepton
    tagService.create(tagDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Deletes existing tag
   * @param id id of the tag to delete
   * @return return ResponseEntity containing only http status (without body)
   */
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") int id) {
    tagService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Searches for all existing tags tags
   * @param page number of the page to display
   * @param pageSize number of elements per page
   * @return ResponseEntity containing http status and list with founded tags
   */
  @GetMapping
  public ResponseEntity<List<TagDto>> findAll(
      @RequestParam(required = true, name = "page") int page,
      @RequestParam(required = true, name = "pageSize") int pageSize) {
    List<TagDto> tagDtos = (List<TagDto>) pagination.paginate(tagService.findAll(), page, pageSize);
    tagDtos.forEach(tagHateoas::getSelfLink);
    return new ResponseEntity<>(tagDtos, HttpStatus.OK);
  }

  /**
   * Searches for tag by name
   * @param name name of the tag to find
   * @return ResponseEntity containing http status and founded tag
   */
  @GetMapping(value = "/{name}")
  public ResponseEntity<TagDto> findByName(@PathVariable("name") String name){
    TagDto tagDto = tagService.find(name);
    tagHateoas.getSelfLink(tagDto);
    return new ResponseEntity<>(tagDto, HttpStatus.OK);
  }

  /**
   * Searches for the most widely used tag of a user with the highest cost of all orders   *
   * @return ResponseEntity containing http status and founded tag
   */
  @GetMapping(value = "/find")
  public ResponseEntity<TagDto> findTag() {
    TagDto tagDto = mostWidelyUsedTagService.findTag();
    tagHateoas.getSelfLink(tagDto);
    return new ResponseEntity<>(tagDto, HttpStatus.OK);
  }
}
