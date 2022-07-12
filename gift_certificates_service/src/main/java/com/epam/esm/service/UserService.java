package com.epam.esm.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mappers.UserMapper;
import com.epam.esm.repos.UserRepository;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    users.forEach(u -> totalCosts.put(u.getId(), orderService.countUserAllOrdersCost(u.getId())));
    Map.Entry<Long, BigDecimal> maxEntry = null;
    for (Map.Entry<Long, BigDecimal> entry : totalCosts.entrySet()) {
      if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
        maxEntry = entry;
      }
    }
    return maxEntry.getKey();
  }
}
