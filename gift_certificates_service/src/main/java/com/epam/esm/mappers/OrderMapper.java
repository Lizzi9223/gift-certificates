package com.epam.esm.mappers;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.utils.Mapper;
import java.util.List;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Mapper between {@code OrderDto} and {@code Order}
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Component
@ComponentScan("com.epam.esm")
public class OrderMapper {
  private final ModelMapper modelMapper;

  @Autowired
  public OrderMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /**
   * Converts {@code Order} object to {@code OrderDto} object
   *
   * @param order model object to convert
   * @return converted dto object
   */
  public OrderDto convertToDto(Order order) {
    return modelMapper.map(order, OrderDto.class);
  }

  /**
   * Converts {@code Order} list to {@code OrderDto} list
   *
   * @param orderList model objects to convert
   * @return converted dto objects
   */
  public List<OrderDto> convertToDto(List<Order> orderList) {
    return Mapper.convertList(orderList, this::convertToDto);
  }

  /**
   * Converts {@code OrderDto} object to {@code Order} object
   *
   * @param orderDto dto object to convert
   * @return converted model object
   */
  public Order convertToEntity(OrderDto orderDto) {
    return modelMapper.map(orderDto, Order.class);
  }

  /**
   * Converts {@code OrderDto} list to {@code Order} list
   *
   * @param orderDtoList dto objects to convert
   * @return converted model objects
   */
  public List<Order> convertToEntity(List<OrderDto> orderDtoList) {
    return Mapper.convertList(orderDtoList, this::convertToEntity);
  }
}
