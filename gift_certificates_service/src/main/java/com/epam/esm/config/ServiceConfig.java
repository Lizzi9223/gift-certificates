package com.epam.esm.config;

import javax.validation.Validation;
import javax.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** Spring configuration for service layer */
@Configuration
@ComponentScan("com.epam.esm")
public class ServiceConfig {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public Validator getValidator() {
    return Validation.buildDefaultValidatorFactory().getValidator();
  }
}
