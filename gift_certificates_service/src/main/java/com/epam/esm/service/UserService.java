package com.epam.esm.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mappers.UserMapper;
import com.epam.esm.repos.UserRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer for user operations
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Service
public class UserService {
  private static final Logger logger = Logger.getLogger(UserService.class);
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final OrderService orderService;

  @Autowired
  public UserService(
      UserRepository userRepository, UserMapper userMapper, OrderService orderService) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.orderService = orderService;
  }

  /**
   * Searches for user by name
   *
   * @param name name of the user to find
   * @return founded userDto
   */
  public UserDto find(String name) {
    Optional<User> user = userRepository.find(name);
    return user.map(userMapper::convertToDto).orElse(null);
  }

  /**
   * Searches for user by id
   *
   * @param id id of the user to find
   * @return founded userDto
   */
  public UserDto find(int id) {
    Optional<User> user = userRepository.find(id);
    return user.map(userMapper::convertToDto).orElse(null);
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
  public int findUserWithHighestOrdersCost() {
    List<User> users = userRepository.findAll();
    Map<Integer, BigDecimal> totalCosts = new HashMap<>();
    users.forEach(u -> totalCosts.put(u.getId(), orderService.countUserAllOrdersCost(u.getId())));
    Map.Entry<Integer, BigDecimal> maxEntry = null;
    for (Map.Entry<Integer, BigDecimal> entry : totalCosts.entrySet()) {
      if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
        maxEntry = entry;
      }
    }
    return maxEntry.getKey();
  }
}
