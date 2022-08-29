package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class StartSpringBootApplication {

  // If put these beans in SecurityConfig class, circular dependency will occur
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebMvcConfigurer configure(){
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
      }
    };
  }

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
