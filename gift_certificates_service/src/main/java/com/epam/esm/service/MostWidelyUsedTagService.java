package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
   * Searches for the most widely used tag of a user with the highest cost of all orders *
   *
   * @return founded tag
   */
  public TagDto findTag() {
    Long userId = userService.findUserWithHighestOrdersCost();
    List<OrderDto> orderDtos = orderService.findByUserId(userId);
    Map<Long, Integer> tagsCount = new HashMap<>();
    orderDtos.forEach(
        orderDto -> {
          orderDto.getCertificates()
              .forEach(
                  certificate -> {
                    certificate.getTags()
                        .forEach(
                            tag -> {
                              tagsCount.put(tag.getId(), tagsCount.getOrDefault(tag.getId(), 0) + 1);
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
}
