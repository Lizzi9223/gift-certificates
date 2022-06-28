package com.epam.esm.controller.user;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests to /user url (contains CRUD operations with users)
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
  private static final Logger logger = Logger.getLogger(UserController.class);
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Searches for user with provided name
   *
   * @param name name of the user to find
   * @return founded user
   */
  @GetMapping(value = "/{name}")
  public UserDto findByName(@PathVariable("name") String name) {
    return userService.find(name);
  }

  /**
   * Searches for all existing users
   *
   * @return list with founded users
   */
  @GetMapping
  public List<UserDto> findAll() {
    return userService.findAll();
  }
}
