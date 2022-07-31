package com.epam.esm.repos;

import com.epam.esm.config.RepositoryConfig;
import com.epam.esm.entity.Certificate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RepositoryConfig.class)
public class CertificateRepositoryTests {
  @Autowired CertificateRepository certificateRepository;

  @Test
  public void contextLoads() {
    Assertions.assertThat(certificateRepository).isNotNull();
  }

  @Test
  public void findCertificateById_certificateExists_certificateReturned() {
    //
    // Given
    //
    Long id = 1L;

    //
    // When
    //
    Certificate certificate = certificateRepository.findById(id);

    //
    // Then
    //
    Assertions.assertThat(certificate.getName()).isNotNull();
  }
  //
  //  @Test
  //  public void createCertificate_certificateContainsTags_certificateCreatedAndTagsAdded() {
  //    //
  //    // Given
  //    //
  //    Set<Tag> tags =
  //        Stream.of(new Tag("food"), new Tag("newTag"))
  //            .collect(Collectors.toCollection(HashSet::new));
  //    Certificate certificate =
  //        new Certificate(
  //            null,
  //            "testCertificate",
  //            "testDescription",
  //            BigDecimal.TEN,
  //            15,
  //            LocalDateTime.now(),
  //            LocalDateTime.now(),
  //            null,
  //            tags);
  //
  //    //
  //    // When
  //    //
  //    certificateRepository.create(certificate);
  //
  //    //
  //    // Then
  //    //
  //    Assertions.assertThat(certificateRepository.find(certificate.getName())).isNotNull();
  //  }
}
