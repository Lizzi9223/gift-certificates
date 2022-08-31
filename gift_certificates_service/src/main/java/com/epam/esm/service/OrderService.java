package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.handling.OrderExceptions;
import com.epam.esm.mappers.OrderMapper;
import com.epam.esm.repos.CertificateRepository;
import com.epam.esm.repos.OrderRepository;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.group.CreateInfo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer for order operations
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Service
public class OrderService {
  private static final Logger logger = Logger.getLogger(OrderService.class);
  private final OrderExceptions exceptionHandling;
  private final OrderRepository orderRepository;
  private final CertificateService certificateService;
  private final CertificateRepository certificateRepository;
  private final UserService userService;
  private final OrderMapper orderMapper;
  private final DtoValidator dtoValidator;

  @Autowired
  public OrderService(
      OrderExceptions exceptionHandling,
      OrderRepository orderRepository,
      CertificateService certificateService,
      CertificateRepository certificateRepository,
      UserService userService,
      OrderMapper orderMapper,
      DtoValidator dtoValidator) {
    this.exceptionHandling = exceptionHandling;
    this.orderRepository = orderRepository;
    this.certificateService = certificateService;
    this.certificateRepository = certificateRepository;
    this.orderMapper = orderMapper;
    this.dtoValidator = dtoValidator;
    this.userService = userService;
  }

  /**
   * Creates new order <br>
   * Validation is provided <br>
   *
   * @param orderDto order to create
   * @throws ServiceException when no certificates attached to the order
   */
  public void create(OrderDto orderDto, Long userId) {
    if (Objects.isNull(orderDto.getCertificates()) || orderDto.getCertificates().size() == 0)
      throw exceptionHandling.getOrderIsEmptyException();
    UserDto userDto = (UserDto) userService.findById(userId, true);
    orderDto.setUserId(userDto.getId());
    setOrderTotalCost(orderDto);
    if (Objects.isNull(orderDto.getPurchaseDate())) orderDto.setPurchaseDate(LocalDateTime.now());
    dtoValidator.validate(orderDto, CreateInfo.class);
    Order order = orderMapper.convertToEntity(orderDto);
    prepareOrder(order);
    orderRepository.save(order);
  }

  /**
   * Finds certificates that are set to order and<br>
   * resets them to order containing all their info. <br>
   * And does the same with user that is set to order. <br>
   * If this not done, exception 'some field is null' will occur
   *
   * @param order order to edit
   */
  private void prepareOrder(Order order) {
    List<Certificate> certificates =
        certificateRepository.findExistingCertificates(order.getCertificates());
    order.setCertificates(new HashSet<>(certificates));
    User user = (User) userService.findById(order.getUser().getId(), false);
    order.setUser(user);
  }

  /**
   * Counts total cost of order and sets it to the order
   *
   * @param orderDto order which total cost to count
   */
  private void setOrderTotalCost(OrderDto orderDto) {
    BigDecimal cost = BigDecimal.ZERO;
    for (int i = 0; i < orderDto.getCertificates().size(); i++) {
      CertificateDto certificateDto =
          certificateService.find(orderDto.getCertificates().get(i).getName());
      cost = cost.add(certificateDto.getPrice());
    }
    orderDto.setPrice(cost);
  }

  /**
   * Searches for order by id
   *
   * @param id id of the order to find
   * @return founded orderDto
   * @throws ServiceException if order with provided id does not exist
   */
  public OrderDto findById(Long id) {
    Optional<Order> order = orderRepository.findById(id);
    if (order.isPresent()) return orderMapper.convertToDto(order.get());
    else throw exceptionHandling.getOrderIdNotExistException(id);
  }

  /**
   * Searches for order by user id
   *
   * @param userId id of the user whose orders to find
   * @return founded orderDto list
   */
  public List<OrderDto> findByUserId(Long userId) {
    User user = (User) userService.findById(userId, false);
    List<Order> orders = orderRepository.findByUser(user);
    return orderMapper.convertToDto(orders);
  }

  /**
   * Counts total cost of all user's orders
   *
   * @param userId id of the user whose total order's cost to count
   * @return total cost of all user's orders
   */
  public BigDecimal countUserAllOrdersCost(Long userId) {
    User user = (User) userService.findById(userId, false);
    List<Order> orders = orderRepository.findByUser(user);
    BigDecimal totalCost = BigDecimal.ZERO;
    for (Order order : orders) {
      if (Objects.nonNull(order.getPrice())) totalCost = totalCost.add(order.getPrice());
      else logger.debug("Order [id=" + order.getId() + "] : total_cost is null");
    }
    return totalCost;
  }
}
