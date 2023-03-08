package com.sswithreactiveapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter) {
    // @formatter:off
    http.authorizeExchange()
        .anyExchange().authenticated()
        .and()
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(jwtAuthenticationConverter);
    // @formatter:on

    return http.build();
  }

  @Bean
  Converter<Jwt, Collection<GrantedAuthority>> customGrantedAuthoritiesConverter(@Value("${app.security.clientId}") String clientId) {
    return new GrantedAuthoritiesExtractor(clientId);
  }

  @Bean
  Converter<Jwt, Mono<AbstractAuthenticationToken>> customJwtAuthenticationConverter(Converter<Jwt, Collection<GrantedAuthority>> converter) {
    return new CustomAuthenticationConverter(converter);
  }

}
