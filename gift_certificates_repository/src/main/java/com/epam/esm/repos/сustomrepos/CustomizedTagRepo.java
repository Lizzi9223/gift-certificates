package com.epam.esm.repos.сustomrepos;

import java.util.List;
import java.util.Set;

public interface CustomizedTagRepo<T> {
  List<T> findExistingTags(Set<T> tags);
}
