package com.sswithreactiveapp.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class CustomAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

  private static final String USERNAME_CLAIM = "preferred_username";
  private final Converter<Jwt, Flux<GrantedAuthority>> jwtGrantedAuthoritiesConverter;

  public CustomAuthenticationConverter(Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
    Assert.notNull(jwtGrantedAuthoritiesConverter, "jwtGrantedAuthoritiesConverter cannot be null");
    this.jwtGrantedAuthoritiesConverter = new ReactiveJwtGrantedAuthoritiesConverterAdapter(
        jwtGrantedAuthoritiesConverter);

    OAuth2TokenValidatorResult.success();
  }

  @Override
  public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {

    // @formatter:off
    return Objects.requireNonNull(this.jwtGrantedAuthoritiesConverter.convert(jwt))
        .collectList()
        .map((authorities) -> new JwtAuthenticationToken(jwt, authorities, extractUsername(jwt)));
    // @formatter:on
  }

  protected String extractUsername(Jwt jwt) {
    return Optional.ofNullable(jwt.getClaimAsString(USERNAME_CLAIM)).orElse(jwt.getSubject());
  }
}
