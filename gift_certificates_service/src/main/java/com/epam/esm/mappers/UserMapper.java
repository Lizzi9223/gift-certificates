package com.epam.esm.mappers;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.utils.Mapper;
import java.util.List;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Mapper between {@code UserDto} and {@code User}
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
@ComponentScan("com.epam.esm")
public class UserMapper {
  private static final Logger logger = Logger.getLogger(UserMapper.class);
  private final ModelMapper modelMapper;

  @Autowired
  public UserMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }
  /**
   * Converts {@code User} object to {@code UserDto} object
   *
   * @param user model object to convert
   * @return converted dto object
   */
  public UserDto convertToDto(User user) {
    return modelMapper.map(user, UserDto.class);
  }

  /**
   * Converts {@code User} list to {@code UserDto} list
   *
   * @param userList model objects to convert
   * @return converted dto objects
   */
  public List<UserDto> convertToDto(List<User> userList) {
    return Mapper.convertList(userList, this::convertToDto);
  }

  /**
   * Converts {@code UserDto} object to {@code User} object
   *
   * @param userDto dto object to convert
   * @return converted model object
   */
  public User convertToEntity(UserDto userDto) {
    return modelMapper.map(userDto, User.class);
  }

  /**
   * Converts {@code UserDto} list to {@code User} list
   *
   * @param userDtos dto objects to convert
   * @return converted model objects
   */
  public List<User> convertToEntity(List<UserDto> userDtos) {
    return Mapper.convertList(userDtos, this::convertToEntity);
  }
}
