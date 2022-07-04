package com.epam.esm.controller.order;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.OrderService;
import com.epam.esm.utils.pagination.Pagination;
import com.epam.esm.utils.hateoas.OrderHateoas;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  private final Pagination pagination;
  private final OrderHateoas orderHateoas;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public OrderController(
      OrderService orderService, Pagination pagination, OrderHateoas orderHateoas, ResourceBundleMessageSource messageSource) {
    this.orderService = orderService;
    this.pagination = pagination;
    this.orderHateoas = orderHateoas;
    this.messageSource = messageSource;
  }

  /**
   * Searches for all user's orders
   *
   * @param userId id of the user whose orders to find
   * @return ResponseEntity containing http status and list of founded orders
   */
  @GetMapping(value = "/user/{userId}")
  public ResponseEntity<List<OrderDto>> findByUserId(
      @PathVariable("userId") int userId,
      @RequestParam(required = true, name = "page") int page,
      @RequestParam(required = true, name = "pageSize") int pageSize) {
    List<OrderDto> orderDtos = (List<OrderDto>) pagination.paginate(orderService.findByUserId(userId), page, pageSize);
    orderDtos.forEach(orderHateoas::getLinks);
    return new ResponseEntity<>(orderDtos, HttpStatus.OK);
  }

  /**
   * Searches for order's cost and timestamp of a purchase by order's id
   *
   * @param id id of the user whose orders to find
   * @return ResponseEntity containing http status and founded order info
   */
  @GetMapping(value = "/{id}")
  public ResponseEntity<OrderInfo> findById(@PathVariable("id") int id) {
    return new ResponseEntity<>(new OrderInfo(orderService.findById(id)), HttpStatus.OK);
  }

  /**
   * Creates new order
   *
   * @param orderDto order to create
   * @return ResponseEntity containing only http status (without body)
   */
  @PostMapping(value = "/user/{userId}")
  public ResponseEntity<Void> create(@RequestBody OrderDto orderDto, @PathVariable("userId") int userId) {
    if (Objects.isNull(orderDto.getCertificates()) || orderDto.getCertificates().size() == 0){
      logger.error("Order is empty");
      throw new OrderIsEmptyException(
          messageSource.getMessage(
              "message.controller.orderIsEmpty",
              new Object[] {},
              LocaleContextHolder.getLocale()));
    }
    orderDto.setUserId(userId);
    orderService.create(orderDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
