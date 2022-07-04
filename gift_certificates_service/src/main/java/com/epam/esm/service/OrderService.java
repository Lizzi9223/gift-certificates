package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.mappers.CertificateMapper;
import com.epam.esm.mappers.OrderMapper;
import com.epam.esm.repos.CertificateRepository;
import com.epam.esm.repos.OrderCertificateRepository;
import com.epam.esm.repos.OrderRepository;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.group.CreateInfo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
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
  private final OrderRepository orderRepository;
  private final OrderCertificateRepository orderCertificateRepository;
  private final CertificateService certificateService;
  private final OrderMapper orderMapper;
  private final DtoValidator dtoValidator;

  @Autowired
  public OrderService(
      OrderRepository orderRepository,
      OrderCertificateRepository orderCertificateRepository,
      CertificateService certificateService,
      OrderMapper orderMapper,
      DtoValidator dtoValidator) {
    this.orderRepository = orderRepository;
    this.orderCertificateRepository = orderCertificateRepository;
    this.certificateService = certificateService;
    this.orderMapper = orderMapper;
    this.dtoValidator = dtoValidator;
  }

  /**
   * Creates new order <br>
   * Validation is provided <br>
   *
   * @param orderDto order to create
   */
  public void create(OrderDto orderDto) {
    orderDto.setPurchaseDate(LocalDateTime.now());
    dtoValidator.validate(orderDto, CreateInfo.class);
    Order order = orderMapper.convertToEntity(orderDto);
    if (Objects.nonNull(orderDto.getCertificates())) {
      int[] certificateIds = new int[orderDto.getCertificates().size()];
      BigDecimal cost = BigDecimal.ZERO;
      for (int i = 0; i < orderDto.getCertificates().size(); i++) {
        CertificateDto certificateDto =
            certificateService.find(orderDto.getCertificates().get(i).getName());
        certificateIds[i] = certificateDto.getId();
        cost = cost.add(certificateDto.getPrice());
      }
      int orderId = orderRepository.create(order);
      orderRepository.setCostToOrder(orderId, cost);
      orderCertificateRepository.create(orderId, certificateIds);
    }
  }

  /**
   * Searches for order by id
   *
   * @param id id of the order to find
   * @return founded orderDto
   */
  public OrderDto findById(int id) {
    Order order = orderRepository.findById(id);
    OrderDto orderDto = orderMapper.convertToDto(order);
    setOrderCertificates(orderDto);
    return orderDto;
  }

  /**
   * Searches for order by user id
   *
   * @param userId id of the user whose orders to find
   * @return founded orderDto list
   */
  public List<OrderDto> findByUserId(int userId) {
    List<Order> orders = orderRepository.findAllUserOrders(userId);
    List<OrderDto> orderDtos = orderMapper.convertToDto(orders);
    orderDtos.forEach(this::setOrderCertificates);
    return orderDtos;
  }

  /**
   * Searches for all existing orders
   *
   * @return list with all existing orderDto
   */
  public List<OrderDto> findAll() {
    List<Order> orders = orderRepository.findAll();
    List<OrderDto> orderDtos = orderMapper.convertToDto(orders);
    orderDtos.forEach(this::setOrderCertificates);
    return orderDtos;
  }

  /**
   * Attaches order certificates to ir
   *
   * @param orderDto order which certificates to find and attach
   */
  private void setOrderCertificates(OrderDto orderDto) {
    int[] certificateIds = orderCertificateRepository.getOrderCertificatesIds(orderDto.getId());
    List<CertificateDto> certificateDtos = new ArrayList<>();
    IntStream.range(0, certificateIds.length)
        .forEach(index -> certificateDtos.add(certificateService.findById(certificateIds[index])));
    orderDto.setCertificates(certificateDtos);
  }

  public BigDecimal countUserAllOrdersCost(int userId) {
    List<Order> orders = orderRepository.findAllUserOrders(userId);
    BigDecimal totalCost = BigDecimal.ZERO;
    for (Order order : orders){
      if(Objects.nonNull(order.getPrice())) totalCost = totalCost.add(order.getPrice());
      else logger.debug("Order [id=" + order.getId() + "] : total_cost is null");
    }
    return totalCost;
  }
}
