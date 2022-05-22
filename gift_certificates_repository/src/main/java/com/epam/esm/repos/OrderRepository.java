package com.epam.esm.repos;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceAlreadyExistExcepton;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repos.metadata.TableField;
import com.epam.esm.repos.query.CertificateSQL;
import com.epam.esm.repos.query.OrderSQL;
import com.epam.esm.repos.query.UserSQL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
public class OrderRepository {
  private static final Logger logger = Logger.getLogger(CertificateRepository.class);
  @PersistenceContext
  private final EntityManager entityManager;
  private final ResourceBundleMessageSource messageSource;

  @Autowired
  public OrderRepository(
      EntityManager entityManager, ResourceBundleMessageSource messageSource) {
    this.entityManager = entityManager;
    this.messageSource = messageSource;
  }

  /**
   * Creates new order <br>   *
   * @param order order to create
   * @return created order id
   */
  public int create(Order order) {
    if (Objects.isNull(order.getPurchaseDate()))
      order.setPurchaseDate(LocalDateTime.now());
    entityManager.getTransaction().begin();
    entityManager.persist(order);
    entityManager.getTransaction().commit();
    return order.getId();
  }

  /**
   * Searches for order with provided id <br>
   * If order with provided id does not exist, {@code ResourceNotFoundException} is thrown
   *
   * @param id id of the order to search for
   * @return founded order
   */
  public Optional<Order> findById(int id){
    try {
      return Optional.of(entityManager.find(Order.class, id));
    } catch (NoResultException e) {
      logger.error("Order {id ='" + id + "'} does not exist");
      throw new ResourceNotFoundException(
          messageSource.getMessage(
              "message.repository.orderIdNotExists",
              new Object[] {id},
              LocaleContextHolder.getLocale()),
          e);
    }
  }

  /**
   * Searches for all orders that belong to the user with provided id <br>   *
   * @param id id of the user whose orders to search for
   * @return list of founded orders
   */
  public List<Order> findAllUserOrders(int id){
    return (List<Order>)
        entityManager
            .createNativeQuery(OrderSQL.FIND_BY_USER, Order.class)
            .setParameter(TableField.ID, id)
            .getResultList();
  }

  /**
   * Searches for all orders   *
   * @return list of all existing orders
   */
  public List<Order> findAll(){
    return entityManager.createQuery(OrderSQL.FIND_ALL, Order.class).getResultList();
  }
}
