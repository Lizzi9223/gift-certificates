package com.epam.esm.repos;

import com.epam.esm.entity.Tag;
import com.epam.esm.repos.сustomrepos.CustomizedTagRepo;
import java.util.Optional;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ComponentScan("com.epam.esm")
public interface TagRepository extends JpaRepository<Tag, Long>, CustomizedTagRepo<Tag> {
  Optional<Tag> findByName(String name);
}
