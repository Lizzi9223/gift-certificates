package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import com.epam.esm.utils.hateoas.UserHateoas;
import java.security.InvalidParameterException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  private final UserService userService;
  private final UserHateoas userHateoas;

  @Autowired
  public UserController(UserService userService, UserHateoas userHateoas) {
    this.userService = userService;
    this.userHateoas = userHateoas;
  }

  /**
   * Searches for user with provided name
   *
   * @param login login of the user to find
   * @return founded user
   */
  @GetMapping(value = "/{login}")
  public ResponseEntity<UserDto> findByLogin(@PathVariable("login") String login) {
    UserDto userDto = userService.find(login);
    userHateoas.getLinks(userDto);
    return new ResponseEntity<>(userDto, HttpStatus.OK);
  }

  /**
   * Searches for all existing users
   *
   * @param pageable for pagination implementation
   * @return list with founded users
   */
  @GetMapping
  public ResponseEntity<List<UserDto>> findAll(Pageable pageable) {
    if (pageable.getPageSize() < 0 || pageable.getPageNumber() < 0)
      throw new InvalidParameterException(
          "Number of page or page size in pageable must be integer numbers and cannot be less than 1");
    List<UserDto> userDtos = userService.findAll(pageable);
    userDtos.forEach(userHateoas::getLinks);
    return new ResponseEntity<>(userDtos, HttpStatus.OK);
  }
}
