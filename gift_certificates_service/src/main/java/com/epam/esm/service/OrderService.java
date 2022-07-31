package com.epam.esm.service;

import com.epam.esm.consts.MessageKeysService;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mappers.CertificateMapper;
import com.epam.esm.mappers.OrderMapper;
import com.epam.esm.repos.CertificateRepository;
import com.epam.esm.repos.OrderRepository;
import com.epam.esm.repos.UserRepository;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.group.CreateInfo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
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
  private final OrderRepository orderRepository;
  private final CertificateRepository certificateRepository;
  private final CertificateService certificateService;
  private final UserRepository userRepository;
  private final OrderMapper orderMapper;
  private final DtoValidator dtoValidator;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public OrderService(
      OrderRepository orderRepository,
      CertificateRepository certificateRepository,
      CertificateService certificateService,
      UserRepository userRepository,
      OrderMapper orderMapper,
      CertificateMapper certificateMapper,
      DtoValidator dtoValidator,
      ResourceBundleMessageSource messageSource) {
    this.orderRepository = orderRepository;
    this.certificateRepository = certificateRepository;
    this.certificateService = certificateService;
    this.orderMapper = orderMapper;
    this.dtoValidator = dtoValidator;
    this.messageSource = messageSource;
    this.userRepository = userRepository;
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
      throw getOrderIsEmptyException();
    orderDto.setUserId(userId);
    setOrderTotalCost(orderDto);
    if (Objects.isNull(orderDto.getPurchaseDate())) orderDto.setPurchaseDate(LocalDateTime.now());
    dtoValidator.validate(orderDto, CreateInfo.class);
    Order order = orderMapper.convertToEntity(orderDto);
    prepareOrder(order);
    orderRepository.create(order);
  }

  /**
   * Finds certificates that are set to order and resets them to order containing all their info
   * <br>
   * and does the same with user that is set to order. <br>
   * If this not done, exception 'some field is null' will occur
   *
   * @param order order to edit
   */
  private void prepareOrder(Order order) {
    Set<Certificate> certificates = new HashSet<>();
    order
        .getCertificates()
        .forEach(
            c -> {
              Certificate cert = certificateRepository.find(c.getName());
              certificates.add(cert);
            });
    order.setCertificates(certificates);

    order.setUser(userRepository.find(order.getUser().getId()));
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
   */
  public OrderDto findById(Long id) {
    Order order = orderRepository.findById(id);
    return orderMapper.convertToDto(order);
  }

  /**
   * Searches for order by user id
   *
   * @param userId id of the user whose orders to find
   * @return founded orderDto list
   */
  public List<OrderDto> findByUserId(Long userId) {
    userRepository.find(userId);
    List<Order> orders = orderRepository.findAllUserOrders(userId);
    return orderMapper.convertToDto(orders);
  }

  /**
   * Counts total cost of all user's orders
   *
   * @param userId id of the user whose total order's cost to count
   * @return total cost of all user's orders
   */
  public BigDecimal countUserAllOrdersCost(Long userId) {
    List<Order> orders = orderRepository.findAllUserOrders(userId);
    BigDecimal totalCost = BigDecimal.ZERO;
    for (Order order : orders) {
      if (Objects.nonNull(order.getPrice())) totalCost = totalCost.add(order.getPrice());
      else logger.debug("Order [id=" + order.getId() + "] : total_cost is null");
    }
    return totalCost;
  }

  private ServiceException getOrderIsEmptyException() {
    logger.error("Order is empty");
    throw new ServiceException(
        messageSource.getMessage(
            MessageKeysService.EMPTY_ORDER, new Object[] {}, LocaleContextHolder.getLocale()));
  }
}
