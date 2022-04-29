package com.epam.esm.repos;

import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ResourceNotFoundException;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

@Repository
@ComponentScan("com.epam.esm")
public class CertificateRepository {
  private static final Logger logger = Logger.getLogger(CertificateRepository.class);
  private final EntityManagerFactory entityManagerFactory;

  @Autowired
  public CertificateRepository(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  //  @PersistenceUnit(unitName = "gift-certificates")
//  EntityManager em;

  public Optional<Certificate> find(String name){
    //EntityManagerFactory emf = Persistence.createEntityManagerFactory( "gift-certificates" );
    try{
      return Optional.of(
          entityManagerFactory
              .createEntityManager()
              .createQuery("from gift_certificate where name = :name", Certificate.class)
              .setParameter("name", name)
              .getSingleResult());
    }catch(NoResultException e){
      logger.error("Certificate {name='" + name + "'} does not exist");
      throw new ResourceNotFoundException("Certificate {name='" + name + "'} does not exist", e);
    }
  }
}
