package com.epam.esm.config;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.hibernate.envers.AuditReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Spring configuration for repository layer (prod profile)
 *
 * @author Lizaveta Yakauleva
 * @version 1.0
 */
@Configuration
@ComponentScan("com.epam.esm")
@Profile("prod")
public class ProdRepositoryConfig {
  @Bean
  public EntityManager getEntityManager(){
    return Persistence.createEntityManagerFactory( "gift-certificates" ).createEntityManager();
  }
  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
    resourceBundleMessageSource.setBasename("messages");
    resourceBundleMessageSource.setDefaultEncoding("UTF-8");
    resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
    return resourceBundleMessageSource;
  }
}
