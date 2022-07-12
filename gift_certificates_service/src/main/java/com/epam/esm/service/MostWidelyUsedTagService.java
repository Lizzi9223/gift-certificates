package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.mappers.TagMapper;
import com.epam.esm.repos.TagRepository;
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
      TagRepository tagRepository,
      TagMapper tagMapper,
      UserService userService,
      OrderService orderService,
      TagService tagService) {
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
        o -> {
          o.getCertificates()
              .forEach(
                  c -> {
                    c.getTags()
                        .forEach(
                            t -> {
                              tagsCount.put(t.getId(), tagsCount.getOrDefault(t.getId(), 0) + 1);
                            });
                  });
        });
    Long tagId =
        tagsCount.entrySet().stream()
            .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
            .get()
            .getKey();
    return tagService.findById(tagId);
  }
}
