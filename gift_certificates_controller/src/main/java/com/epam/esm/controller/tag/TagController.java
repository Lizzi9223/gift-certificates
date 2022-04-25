package com.epam.esm.controller.tag;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests to /tag url
 * (contains CRD operations with tags)
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/tag")
public class TagController {
  private static final Logger logger = Logger.getLogger(TagController.class);
  //private final TagService tagService;

//  @Autowired
//  public TagController(TagService tagService) {
//    this.tagService = tagService;
//  }

  /**
   * Creates new tag
   * @param tagDto tag to create
   */
//  @PostMapping
//  public void create(@RequestBody TagDto tagDto) throws ResourceAlreadyExistExcepton {
//    tagService.create(tagDto);
//  }

  /**
   * Deletes existing tag
   * @param id id of the tag to delete
   */
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable("id") int id) {
    //tagService.delete(id);
  }

  /**
   * Searches for tags
   * if {@code name} is null, method searches for all tags, otherwise for the tag with provided name
   * @param name name of the tag to find
   * @return list with founded tags
   */
  @GetMapping
  public List<Object> find(@RequestParam(required = false, name = "name") String name) { //List<TagDto>
//    if (Objects.isNull(name)) return tagService.findAll();
//    else return Collections.singletonList(tagService.find(name));
    return new ArrayList<>();
  }
}
