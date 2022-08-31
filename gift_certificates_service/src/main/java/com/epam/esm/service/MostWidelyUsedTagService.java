package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Contains methods to find the most widely used tag of a user<br>
 * who has the highest cost of all orders
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Service
public class MostWidelyUsedTagService {
  private final UserService userService;
  private final OrderService orderService;
  private final TagService tagService;

  @Autowired
  public MostWidelyUsedTagService(
      UserService userService, OrderService orderService, TagService tagService) {
    this.userService = userService;
    this.orderService = orderService;
    this.tagService = tagService;
  }
  /**
   * Searches for the most widely used tag of a user<br>
   * who has the highest cost of all orders
   *
   * @return founded tag
   */
  public TagDto findTag() {
    Long userId = findUserWithHighestOrdersCost();
    List<OrderDto> orderDtos = orderService.findByUserId(userId);
    Map<Long, Integer> tagsCount = new HashMap<>();
    orderDtos.forEach(
        orderDto -> {
          orderDto
              .getCertificates()
              .forEach(
                  certificate -> {
                    certificate
                        .getTags()
                        .forEach(
                            tag -> {
                              tagsCount.put(
                                  tag.getId(), tagsCount.getOrDefault(tag.getId(), 0) + 1);
                            });
                  });
        });
    Long tagId =
        tagsCount.entrySet().stream()
            .max((tagFirst, tagSecond) -> tagFirst.getValue() > tagSecond.getValue() ? 1 : -1)
            .get()
            .getKey();
    return tagService.findById(tagId);
  }

  /**
   * Searches for a user with the highest cost of all orders
   *
   * @return founded userDto
   */
  private Long findUserWithHighestOrdersCost() {
    List<UserDto> users = userService.findAll();
    Map<Long, BigDecimal> totalCosts = new HashMap<>();
    users.forEach(
        user -> totalCosts.put(user.getId(), orderService.countUserAllOrdersCost(user.getId())));
    Map.Entry<Long, BigDecimal> maxEntry = null;
    for (Map.Entry<Long, BigDecimal> entry : totalCosts.entrySet()) {
      if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
        maxEntry = entry;
      }
    }
    if (Objects.nonNull(Objects.requireNonNull(maxEntry).getKey())) return maxEntry.getKey();
    else return null;
  }
}
