package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class StartSpringBootApplication{
  public static void main(String[] args) {
    SpringApplication.run(StartSpringBootApplication.class, args);

//    ConfigurableEnvironment environment = new StandardEnvironment();
//    environment.setActiveProfiles("dev");
//
//    SpringApplication application = new SpringApplication(StartSpringBootApplication.class);
//    application.setEnvironment(environment);
//    application.setAdditionalProfiles("test");
//    application.run(args);
  }
}
