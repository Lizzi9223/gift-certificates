package com.epam.esm.utils.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.epam.esm.controller.order.OrderController;
import com.epam.esm.dto.OrderDto;
import org.springframework.stereotype.Component;

/**
 * Contains methods for hateoas implementation for OrderController *
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
public class OrderHateoas {
  /**
   * adds links to object
   *
   * @param orderDto object links added to
   */
  public void getLinks(OrderDto orderDto) {
    orderDto.add(linkTo(methodOn(OrderController.class).findById(orderDto.getId())).withSelfRel());
    orderDto.add(
        linkTo(methodOn(OrderController.class).findByUserId(orderDto.getUserId(), 1, 100))
            .withRel("allUserOrders"));
  }
}
