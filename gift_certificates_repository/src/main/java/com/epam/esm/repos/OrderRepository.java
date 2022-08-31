package com.epam.esm.repos;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import java.util.List;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ComponentScan("com.epam.esm")
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUser(User user);

  Page<Order> findByUser(User user, Pageable pageable);
}
