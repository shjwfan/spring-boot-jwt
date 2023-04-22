package org.nocontrib.configurer;

import org.nocontrib.security.JwtConfigurer;
import org.nocontrib.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

  private static final String AUTH_SIGN_IN_ENDPOINT = "/api/auth/sign/in";
  private static final String AUTH_SIGN_UP_ENDPOINT = "/api/auth/sign/up";

  private final JwtFilter filter;

  public SecurityConfigurer(JwtFilter filter) {
    this.filter = filter;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic().disable()
        .csrf().disable()
        .sessionManagement().disable()
        .authorizeRequests()
        .antMatchers(AUTH_SIGN_IN_ENDPOINT).permitAll()
        .antMatchers(AUTH_SIGN_UP_ENDPOINT).permitAll()
        .antMatchers("/api/account/**").hasRole("ACCOUNT")
        .anyRequest().authenticated()
        .and()
        .apply(new JwtConfigurer(filter));
  }
}
