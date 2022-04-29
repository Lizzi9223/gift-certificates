package com.epam.esm.mappers;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper between {@code TagDto} and {@code Tag}
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class TagMapper {
  private static final Logger logger = Logger.getLogger(TagMapper.class);
  private final ModelMapper modelMapper;

  @Autowired
  public TagMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /**
   * Converts {@code Tag} object to {@code TagDto} object
   *
   * @param tag model object to convert
   * @return converted dto object
   */
//  public TagDto convertToDto(Tag tag) {
//    TagDto tagDto = modelMapper.map(tag, TagDto.class);
//    return tagDto;
//  }
//
//  /**
//   * Converts {@code Tag} list to {@code TagDto} list
//   *
//   * @param tags model objects to convert
//   * @return converted dto objects
//   */
//  public List<TagDto> convertToDto(List<Tag> tags) {
//    List<TagDto> tagDtos = Mapper.convertList(tags, this::convertToDto);
//    return tagDtos;
//  }
//
//  /**
//   * Converts {@code TagDto} object to {@code Tag} object
//   *
//   * @param tagDto dto object to convert
//   * @return converted model object
//   */
//  public Tag convertToEntity(TagDto tagDto) {
//    Tag tag = modelMapper.map(tagDto, Tag.class);
//    return tag;
//  }
//
//  /**
//   * Converts {@code TagDto} list to {@code Tag} list
//   *
//   * @param tagDtos dto objects to convert
//   * @return converted model objects
//   */
//  public List<Tag> convertToEntity(List<TagDto> tagDtos) {
//    List<Tag> tags = Mapper.convertList(tagDtos, this::convertToEntity);
//    return tags;
//  }
}
