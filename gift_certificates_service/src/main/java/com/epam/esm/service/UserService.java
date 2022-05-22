package com.epam.esm.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mappers.UserMapper;
import com.epam.esm.repos.UserRepository;
import com.epam.esm.validator.DtoValidator;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service layer for user operations
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class UserService {
  private static final Logger logger = Logger.getLogger(UserService.class);
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final DtoValidator dtoValidator;

  @Autowired
  public UserService(
      UserRepository userRepository, UserMapper userMapper, DtoValidator dtoValidator) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.dtoValidator = dtoValidator;
  }

  /**
   * Searches for user by name
   *
   * @param name name of the user to find
   * @return founded userDto
   */
  public UserDto find(String name) {
    Optional<User> user = userRepository.find(name);
    if (user.isPresent()) {
      UserDto userDto = userMapper.convertToDto(user.get());
      return userDto;
    } else return null;
  }

  /**
   * Searches for user by id
   *
   * @param id id of the user to find
   * @return founded userDto
   */
  public UserDto find(int id) {
    Optional<User> user = userRepository.find(id);
    if (user.isPresent()) {
      UserDto userDto = userMapper.convertToDto(user.get());
      return userDto;
    } else return null;
  }

  /**
   * Searches for all existing users
   *
   * @return list of all existing users
   */
  public List<UserDto> findAll() {
    List<User> users = userRepository.findAll();
    return userMapper.convertToDto(users);
  }
}
