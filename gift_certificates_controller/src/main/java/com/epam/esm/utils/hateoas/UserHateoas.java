package com.epam.esm.utils.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.order.OrderController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import org.springframework.stereotype.Component;

/**
 * Contains methods for hateoas implementation for UserController
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class UserHateoas {
  /**
   * adds links to object
   *
   * @param userDto object links added to
   */
  public void getLinks(UserDto userDto) {
    userDto.add(linkTo(methodOn(UserController.class).findByLogin(userDto.getLogin())).withSelfRel());
    userDto.add(
        linkTo(methodOn(OrderController.class).findByUserId(userDto.getId(), 1, 100))
            .withRel("orders"));
  }
}
