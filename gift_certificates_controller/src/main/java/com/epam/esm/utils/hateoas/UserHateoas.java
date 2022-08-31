package com.epam.esm.utils.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.UserController;
import com.epam.esm.controller.order.OrderController;
import com.epam.esm.dto.UserDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
   * @param pageable for pagination implementation
   */
  public void getLinks(UserDto userDto, Pageable pageable) {
    userDto.add(
        linkTo(methodOn(UserController.class).findByLogin(userDto.getLogin())).withSelfRel());
    userDto.add(
        linkTo(methodOn(OrderController.class).findByUserId(userDto.getId(), PageRequest.of(1, 1000)))
            .withRel("orders"));
  }

  /**
   * adds links to object
   *
   * @param userDto object links added to
   */
  public void getLinks(UserDto userDto) {
    userDto.add(
        linkTo(methodOn(UserController.class).findByLogin(userDto.getLogin())).withSelfRel());
    userDto.add(
        linkTo(
                methodOn(OrderController.class)
                    .findByUserId(userDto.getId(), PageRequest.of(1, 1000)))
            .withRel("orders"));
  }
}
