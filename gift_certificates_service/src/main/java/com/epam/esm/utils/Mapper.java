package com.epam.esm.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * List mapper
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
public class Mapper {
  public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
    return list.stream().map(converter::apply).collect(Collectors.toList());
  }
}
