package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.handling.TagExceptions;
import com.epam.esm.mappers.TagMapper;
import com.epam.esm.repos.TagRepository;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.group.CreateInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Service layer for tag operations
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Service
public class TagService {
  private final TagExceptions exceptionHandling;
  private final TagRepository tagRepository;
  private final TagMapper tagMapper;
  private final DtoValidator dtoValidator;

  @Autowired
  public TagService(
      TagExceptions exceptionHandling,
      TagRepository tagRepository,
      TagMapper tagMapper,
      DtoValidator dtoValidator) {
    this.exceptionHandling = exceptionHandling;
    this.tagRepository = tagRepository;
    this.tagMapper = tagMapper;
    this.dtoValidator = dtoValidator;
  }

  /**
   * Creates new tag <br>
   * Validation is provided
   *
   * @param tagDto tag to create
   * @throws ServiceException when <br>
   *     tag with the provided name already exists
   */
  public void create(TagDto tagDto) {
    dtoValidator.validate(tagDto, CreateInfo.class);
    Tag tag = tagMapper.convertToEntity(tagDto);
    try {
      tagRepository.save(tag);
    } catch (DataIntegrityViolationException e) {
      throw exceptionHandling.getExceptionForTagNameAlreadyExist(e, tagDto.getName());
    }
  }

  /**
   * Searches for tag by name
   *
   * @param name name of the tag to find
   * @return founded tagDto
   * @throws ServiceException when <br>
   *     tag with the provided name does not exist
   */
  public TagDto findByName(String name) {
    Optional<Tag> tag = tagRepository.findByName(name);
    if (tag.isPresent()) return tagMapper.convertToDto(tag.get());
    else throw exceptionHandling.getExceptionForTagNameNotExist(name);
  }

  /**
   * Searches for tag by id
   *
   * @param id id of the tag to find
   * @return founded tagDto
   * @throws ServiceException when <br>
   *     tag with the provided id does not exist
   */
  public TagDto findById(Long id) {
    Optional<Tag> tag = tagRepository.findById(id);
    if (tag.isPresent()) return tagMapper.convertToDto(tag.get());
    else throw exceptionHandling.getExceptionForTagIdNotExist(id);
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
   * @param id id of a tag to delete
   * @throws ServiceException when <br>
   *     tag with the provided id does not exist
   */
  public void delete(Long id) {
    Optional<Tag> tag = tagRepository.findById(id);
    if (tag.isPresent()) tagRepository.delete(tag.get());
    else throw exceptionHandling.getExceptionForTagIdNotExist(id);
  }
}
