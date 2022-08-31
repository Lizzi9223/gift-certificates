package com.epam.esm.service;

import com.epam.esm.consts.UserRoles;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.handling.UserExceptions;
import com.epam.esm.mappers.UserMapper;
import com.epam.esm.repos.UserRepository;
import com.epam.esm.security.UserDetailsImpl;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.group.CreateInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service layer for user operations
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Service
public class UserService implements UserDetailsService {
  private final UserExceptions exceptionHandling;
  private final UserRepository userRepository;
  private final RoleService roleService;
  private final UserMapper userMapper;
  private final DtoValidator dtoValidator;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(
      UserExceptions exceptionHandling,
      UserRepository userRepository,
      RoleService roleService,
      UserMapper userMapper,
      DtoValidator dtoValidator,
      PasswordEncoder passwordEncoder) {
    this.exceptionHandling = exceptionHandling;
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.userMapper = userMapper;
    this.dtoValidator = dtoValidator;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Implementation of loadUserByUsername method defined in UserDetailsService interface<br>
   * Searches for user by login
   *
   * @param login login of the user to find
   * @return founded user
   */
  @Override
  public UserDetails loadUserByUsername(String login) {
    Optional<User> user = userRepository.findByLogin(login);
    if (user.isPresent()) return UserDetailsImpl.fromUserEntityToUserDetails(user.get());
    else throw exceptionHandling.getExceptionForUserLoginNotExist(login);
  }

  /**
   * Creates new user (with role USER ONLY !)
   *
   * @param userDto user to create
   */
  public void saveUser(UserDto userDto) {
    RoleDto roleDto = roleService.find(UserRoles.USER.toString());
    userDto.setRoleId(roleDto.getId());
    dtoValidator.validate(userDto, CreateInfo.class);
    userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
    User user = userMapper.convertToEntity(userDto);
    try {
      userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      throw exceptionHandling.getExceptionForUserLoginAlreadyExists(e, userDto.getName());
    }
  }

  /**
   * Searches for user by login
   *
   * @param login login of the user to find
   * @return founded userDto
   * @throws ServiceException when user with provided name does not exist
   */
  public UserDto find(String login) {
    Optional<User> user = userRepository.findByLogin(login);
    if (user.isPresent()) {
      user.get().setPassword("");
      return userMapper.convertToDto(user.get());
    } else throw exceptionHandling.getExceptionForUserLoginNotExist(login);
  }

  /**
   * Searches for user by id <br>
   * and converts it to DTO
   *
   * @param id id of the user to find
   * @param isDTO if it is true, user DTO wil be returned.<br>
   *     if it is false, user entity will be returned
   * @return founded userDto
   * @throws ServiceException when user with provided id does not exist
   */
  public Object findById(Long id, boolean isDTO) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      user.get().setPassword("");
      if (isDTO) return userMapper.convertToDto(user.get());
      else return user.get();
    } else throw exceptionHandling.getExceptionForUserIdNotExist(id);
  }

  /**
   * Searches for all existing users
   *
   * @return list of all existing users
   */
  public List<UserDto> findAll() {
    List<User> users = userRepository.findAll();
    users.forEach(user -> user.setPassword(""));
    return userMapper.convertToDto(users);
  }
}
