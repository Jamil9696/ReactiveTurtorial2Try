package com.sswithreactiveapp.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Configuration
public class JwtConfig {

  @Bean
  Converter<Jwt, Collection<GrantedAuthority>> customGrantedAuthoritiesConverter(@Value("${app.security.clientId}") String clientId) {
    return new GrantedAuthoritiesExtractor(clientId);
  }

  @Bean
  Converter<Jwt, Mono<AbstractAuthenticationToken>> customJwtAuthenticationConverter(Converter<Jwt, Collection<GrantedAuthority>> converter) {
    return new CustomAuthenticationConverter(converter);
  }
}
