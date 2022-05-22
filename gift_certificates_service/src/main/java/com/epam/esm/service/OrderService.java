package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.mappers.CertificateMapper;
import com.epam.esm.mappers.OrderMapper;
import com.epam.esm.repos.CertificateRepository;
import com.epam.esm.repos.OrderCertificateRepository;
import com.epam.esm.repos.OrderRepository;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.group.CreateInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service layer for order operations
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class OrderService {
  private static final Logger logger = Logger.getLogger(OrderService.class);
  private final OrderRepository orderRepository;
  private final OrderCertificateRepository orderCertificateRepository;

  private final CertificateRepository certificateRepository;
  private final OrderMapper orderMapper;
  private final CertificateMapper certificateMapper;
  private final DtoValidator dtoValidator;

  @Autowired
  public OrderService(
      OrderRepository orderRepository,
      OrderCertificateRepository orderCertificateRepository,
      CertificateRepository certificateRepository,
      OrderMapper orderMapper,
      CertificateMapper certificateMapper,
      DtoValidator dtoValidator) {
    this.orderRepository = orderRepository;
    this.orderCertificateRepository = orderCertificateRepository;
    this.certificateRepository = certificateRepository;
    this.orderMapper = orderMapper;
    this.certificateMapper = certificateMapper;
    this.dtoValidator = dtoValidator;
  }

  /**
   * Creates new order <br>
   * Validation is provided <br>
   *
   * @param orderDto order to create
   */
  public void create(OrderDto orderDto) {
    dtoValidator.validate(orderDto, CreateInfo.class);
    Order order = orderMapper.convertToEntity(orderDto);
    int[] certificateIds = getCertificatesIds(orderDto.getCertificates());
    int orderId = orderRepository.create(order);
    if (certificateIds.length != 0) orderCertificateRepository.create(orderId, certificateIds);
  }

  /**
   * @param certificateDtos list of the certificate which id need to find
   * @return int array containing id of certificates from the provided certificatesDto list
   */
  private int[] getCertificatesIds(List<CertificateDto> certificateDtos) {
    if (Objects.nonNull(certificateDtos)) {
      List<String> certificateNames = new ArrayList<>();
      certificateDtos.forEach(certificateDto -> certificateNames.add(certificateDto.getName()));
      int[] certificateIds = new int[certificateNames.size()];
      IntStream.range(0, certificateNames.size())
          .forEach(
              index ->
                  certificateIds[index] =
                      certificateRepository.find(certificateNames.get(index)).get().getId());
      return certificateIds;
    } else return null;
  }

  /**
   * Searches for order by id
   *
   * @param id id of the order to find
   * @return founded orderDto
   */
  public OrderDto findById(int id) {
    Optional<Order> order = orderRepository.findById(id);
    if (order.isPresent()) {
      OrderDto orderDto = orderMapper.convertToDto(order.get());
      setOrderCertificates(orderDto);
      return orderDto;
    } else return null;
  }

  /**
   * Searches for order by user id
   *
   * @param userId id of the user whose orders to find
   * @return founded orderDto list
   */
  public List<OrderDto> find(int userId) {
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
    List<Certificate> certificates =
        orderCertificateRepository.findOrderCertificates(orderDto.getId());
    orderDto.setCertificates(certificateMapper.convertToDto(certificates));
  }
}
