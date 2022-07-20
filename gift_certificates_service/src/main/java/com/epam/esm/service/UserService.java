package com.epam.esm.service;

import com.epam.esm.consts.UserRoles;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.mappers.UserMapper;
import com.epam.esm.repos.RoleRepository;
import com.epam.esm.repos.UserRepository;
import com.epam.esm.security.UserDetailsImpl;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.group.CreateInfo;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
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
  private static final Logger logger = Logger.getLogger(UserService.class);
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserMapper userMapper;
  private final OrderService orderService;
  private final DtoValidator dtoValidator;
  private final PasswordEncoder passwordEncoder;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public UserService(
      UserRepository userRepository,
      RoleRepository roleRepository,
      UserMapper userMapper,
      OrderService orderService,
      DtoValidator dtoValidator,
      PasswordEncoder passwordEncoder,
      ResourceBundleMessageSource messageSource) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.userMapper = userMapper;
    this.orderService = orderService;
    this.dtoValidator = dtoValidator;
    this.passwordEncoder = passwordEncoder;
    this.messageSource = messageSource;
  }

  /**
   * Implementation of loadUserByUsername method defined in UserDetailsService interface
   *
   * @param login login of the user to find
   * @return founded user
   */
  @Override
  public UserDetails loadUserByUsername(String login) {
    User user = userRepository.find(login);
    return UserDetailsImpl.fromUserEntityToUserDetails(user);
  }

  /**
   * Creates new user with role user only
   *
   * @param userDto user to create
   */
  public void saveUser(UserDto userDto) {
    Role role = roleRepository.findByName(UserRoles.USER.toString());
    userDto.setRoleId(role.getId());
    dtoValidator.validate(userDto, CreateInfo.class);
    userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
    User user = userMapper.convertToEntity(userDto);
    userRepository.create(user);
  }

  /**
   * Searches for user by login
   *
   * @param login login of the user to find
   * @return founded userDto
   */
  public UserDto find(String login) {
    User user = userRepository.find(login);
    return userMapper.convertToDto(user);
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

  /**
   * Searches for a user with the highest cost of all orders
   *
   * @return founded userDto
   */
  public Long findUserWithHighestOrdersCost() {
    List<User> users = userRepository.findAll();
    Map<Long, BigDecimal> totalCosts = new HashMap<>();
    users.forEach(user -> totalCosts.put(user.getId(), orderService.countUserAllOrdersCost(user.getId())));
    Map.Entry<Long, BigDecimal> maxEntry = null;
    for (Map.Entry<Long, BigDecimal> entry : totalCosts.entrySet()) {
      if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
        maxEntry = entry;
      }
    }
    return maxEntry.getKey();
  }
}
