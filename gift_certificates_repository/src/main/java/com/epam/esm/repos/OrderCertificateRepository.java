package com.epam.esm.repos;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.OrderCertificate;
import com.epam.esm.entity.key.OrderCertificateId;
import com.epam.esm.exception.ResourceAlreadyExistExcepton;
import com.epam.esm.repos.metadata.TableField;
import com.epam.esm.repos.query.OrderCertificateSQL;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Repository;

@Repository
@ComponentScan("com.epam.esm")
public class OrderCertificateRepository {
  private static final Logger logger = Logger.getLogger(CertificateRepository.class);
  @PersistenceContext private final EntityManager entityManager;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public OrderCertificateRepository(
      EntityManager entityManager, ResourceBundleMessageSource messageSource) {
    this.entityManager = entityManager;
    this.messageSource = messageSource;
  }

  /**
   * Adds certificate to order
   *
   * @param orderCertificate certificateTag to add
   */
  public void create(OrderCertificate orderCertificate) {
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(orderCertificate);
      entityManager.getTransaction().commit();
    } catch (PersistenceException e) {
      entityManager.getTransaction().rollback();
      logger.error(
          "Record {orderId="
              + orderCertificate.getOrderCertificateId().getOrderId()
              + ",certificateId="
              + orderCertificate.getOrderCertificateId().getCertificateId()
              + "} already exists");
      throw new ResourceAlreadyExistExcepton(
          messageSource.getMessage(
              "message.repository.recordOrderHasCertifExists",
              new Object[] {
                orderCertificate.getOrderCertificateId().getOrderId(),
                orderCertificate.getOrderCertificateId().getCertificateId()
              },
              LocaleContextHolder.getLocale()),
          e);
    }
  }

  /**
   * Adds certificates to order
   *
   * @param orderId certificate's id to add tags to
   * @param certificateIds id of tags to add to the certificate
   */
  public void create(int orderId, int[] certificateIds) {
    Arrays.stream(certificateIds)
        .forEach(
            certificateId -> {
              try {
                create(new OrderCertificate(new OrderCertificateId(orderId, certificateId)));
              } catch (ResourceAlreadyExistExcepton e) {
                logger.warn(
                    "Record {orderId="
                        + orderId
                        + ",certificateId="
                        + certificateId
                        + "} already exists");
              }
            });
  }

  /**
   * Searches for all certificates that belong to order with provided id *
   *
   * @param id is of the order that certificates to find
   * @return list of founded certificates
   */
  public List<Certificate> findOrderCertificates(int id) {
    return (List<Certificate>)
        entityManager
            .createNativeQuery(OrderCertificateSQL.FIND_ORDER_CERTIFICATES, Certificate.class)
            .setParameter(TableField.ID, id)
            .getResultList();
  }
}
