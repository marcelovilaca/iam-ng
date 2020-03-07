package it.infn.cnaf.sd.iam.api.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

  public static final Logger LOG = LoggerFactory.getLogger(ApiSecurityConfig.class);

  @Autowired
  AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http.authorizeRequests()
      .mvcMatchers("/api/**")
        .fullyAuthenticated()
        .and()
        .oauth2ResourceServer()
          .authenticationEntryPoint(new OAuth2AuthenticationEntryPoint())
          .authenticationManagerResolver(authenticationManagerResolver)
        .and()
          .csrf().disable();
    // @formatter:on
  }
}
