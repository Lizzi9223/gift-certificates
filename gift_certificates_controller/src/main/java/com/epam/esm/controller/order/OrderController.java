package com.epam.esm.controller.order;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests to /order url (contains CRUD operations with orders)
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {
  private static final Logger logger = Logger.getLogger(OrderController.class);
  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * Searches for all user's orders
   *
   * @param userId id of the user whose orders to find
   * @return list of founded orders
   */
  @GetMapping(value = "/user/{userId}")
  public List<OrderDto> findByUserId(@PathVariable("userId") int userId) {
    return orderService.findByUserId(userId);
  }

  /**
   * Searches for order's cost and timestamp of a purchase by order's id
   *
   * @param id id of the user whose orders to find
   * @return list of founded orders
   */
  @GetMapping(value = "/{id}")
  public OrderInfo findById(@PathVariable("id") int id) {
    return new OrderInfo(orderService.findById(id));
  }

  /**
   * Creates new order
   *
   * @param orderDto order to create
   */
  @PostMapping(value = "/user/{userId}")
  public void create(@RequestBody OrderDto orderDto, @PathVariable("userId") int userId) {
    orderDto.setUserId(userId);
    orderService.create(orderDto);
  }
}
