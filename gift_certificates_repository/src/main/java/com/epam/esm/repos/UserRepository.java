package com.epam.esm.repos;

import com.epam.esm.entity.User;
import java.util.Optional;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ComponentScan("com.epam.esm")
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByLogin(String login);
}
