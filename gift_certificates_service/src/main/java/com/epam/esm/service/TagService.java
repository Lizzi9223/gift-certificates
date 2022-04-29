package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.mappers.TagMapper;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.group.CreateInfo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer for tag operations
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Service
public class TagService {
  private static final Logger logger = Logger.getLogger(TagService.class);
  //private final TagRepository tagRepository;
  private TagMapper tagMapper;
  private final DtoValidator dtoValidator;

  @Autowired
  public TagService(
      //TagRepository tagRepository,
      TagMapper tagMapper, DtoValidator dtoValidator) {
    //this.tagRepository = tagRepository;
    this.tagMapper = tagMapper;
    this.dtoValidator = dtoValidator;
  }

  /**
   * Creates new tag Validation is provided
   *
   * @param tagDto tag to create
   * @return id of the created tag
   */
  public int create(TagDto tagDto)  { //throws ResourceAlreadyExistExcepton
    dtoValidator.validate(tagDto, CreateInfo.class);
//    Tag tag = tagMapper.convertToEntity(tagDto);
//    return tagRepository.create(tag);
    return 0;
  }

  /**
   * Creates new tags
   *
   * @param tagDtoList list of tags to create
   * @return ids of the created tags
   */
  public int[] create(List<TagDto> tagDtoList) {
    tagDtoList.forEach(tagDto -> dtoValidator.validate(tagDto, CreateInfo.class));
//    List<Tag> tags = tagMapper.convertToEntity(tagDtoList);
//    return tagRepository.create(tags);
    return new int[5];
  }

  /**
   * Searches for tag by name
   *
   * @param name name of the tag to find
   * @return founded tagDto
   */
  public TagDto find(String name) {
//    Optional<Tag> tag = tagRepository.find(name);
//    return tagMapper.convertToDto(tag.get());
    return null;
  }

  /**
   * Searches for all existing tags
   *
   * @return list of all existing tags
   */
  public List<TagDto> findAll() {
//    List<Tag> tags = tagRepository.findAll();
//    return tagMapper.convertToDto(tags);
    return new ArrayList<>();
  }

  /**
   * Deletes existing tag <br>
   * If some certificate contains this tag, it will be removed
   *
   * @param id id of the tag to delete
   */
  public void delete(int id) {
    //tagRepository.delete(id);
  }
}
