package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mappers.TagMapper;
import com.epam.esm.repos.TagRepository;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.group.CreateInfo;
import java.util.List;
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
  private final TagRepository tagRepository;
  private final TagMapper tagMapper;
  private final DtoValidator dtoValidator;

  @Autowired
  public TagService(TagRepository tagRepository, TagMapper tagMapper, DtoValidator dtoValidator) {
    this.tagRepository = tagRepository;
    this.tagMapper = tagMapper;
    this.dtoValidator = dtoValidator;
  }

  /**
   * Creates new tag Validation is provided
   *
   * @param tagDto tag to create
   * @return id of the created tag
   */
  public void create(TagDto tagDto) {
    dtoValidator.validate(tagDto, CreateInfo.class);
    Tag tag = tagMapper.convertToEntity(tagDto);
    tagRepository.create(tag);
  }

  /**
   * Searches for tag by name
   *
   * @param name name of the tag to find
   * @return founded tagDto
   */
  public TagDto findByName(String name) {
    Tag tag = tagRepository.findByName(name);
    return tagMapper.convertToDto(tag);
  }

  /**
   * Searches for tag by id
   *
   * @param id id of the tag to find
   * @return founded tagDto
   */
  public TagDto findById(Long id) {
    Tag tag = tagRepository.findById(id);
    return tagMapper.convertToDto(tag);
  }

  /**
   * Searches for all existing tags
   *
   * @return list of all existing tags
   */
  public List<TagDto> findAll() {
    List<Tag> tags = tagRepository.findAll();
    return tagMapper.convertToDto(tags);
  }

  /**
   * Deletes existing tag <br>
   * If some certificate contains this tag, it will be removed
   *
   * @param id id of the tag to delete
   */
  public void delete(Long id) {
    tagRepository.delete(id);
  }
}
