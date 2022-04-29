package com.epam.esm.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class StartSpringBootApplication {
  public static void main(String[] args) {
    //SpringApplication.run(StartSpringBootApplication.class, args);

    ConfigurableEnvironment environment = new StandardEnvironment();
    environment.setActiveProfiles("prod");

    SpringApplication application = new SpringApplication(StartSpringBootApplication.class);
    application.setEnvironment(environment);
    application.setAdditionalProfiles("dev");
    application.run(args);
  }
}
