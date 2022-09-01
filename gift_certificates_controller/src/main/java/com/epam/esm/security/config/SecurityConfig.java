package com.epam.esm.security.config;

import com.epam.esm.security.exception.CustomAccessDeniedHandler;
import com.epam.esm.security.exception.CustomAuthenticationEntryPoint;
import com.epam.esm.security.filter.JwtFilter;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final JwtFilter jwtFilter;
  private final UserService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final CustomAuthenticationEntryPoint unauthorizedHandler;

  @Autowired
  public SecurityConfig(
      JwtFilter jwtFilter,
      UserService userDetailsService,
      PasswordEncoder passwordEncoder,
      CustomAuthenticationEntryPoint unauthorizedHandler) {
    this.jwtFilter = jwtFilter;
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.unauthorizedHandler = unauthorizedHandler;
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and()
        .httpBasic().disable()
        .csrf().disable();

    http.authorizeRequests()
        .antMatchers("/register", "/auth")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/certificate/**", "/tag/**")
        .permitAll()
        .antMatchers("/refresh")
        .authenticated()
        .antMatchers(HttpMethod.GET, "/**")
        .hasRole("USER")
        .antMatchers(HttpMethod.POST, "/order/**")
        .hasRole("USER")
        .antMatchers("/**")
        .hasRole("ADMIN")
        .anyRequest()
        .authenticated()
        .and();

    http.exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .accessDeniedHandler(accessDeniedHandler())
        .and();

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
