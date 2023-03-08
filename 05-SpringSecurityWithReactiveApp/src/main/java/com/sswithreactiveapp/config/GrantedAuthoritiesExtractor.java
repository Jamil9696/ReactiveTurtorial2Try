package com.sswithreactiveapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.util.ObjectUtils;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

@RequiredArgsConstructor
public class GrantedAuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {
  private static final String ROLE_PREFIX = "ROLE_";
  private static final String ROLES = "roles";
  private static final String CLAIM_REALM_ACCESS = "realm_access";
  private static final String RESOURCE_ACCESS = "resource_access";


  private final Converter<Jwt, Collection<GrantedAuthority>> defaultAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

  private final String clientId;

  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {
    List<String> realmRoles = realmRoles(jwt);
    List<String> clientRoles = clientRoles(jwt, clientId);

    Collection<GrantedAuthority> authorities = Stream.concat(realmRoles.stream(), clientRoles.stream())
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
    authorities.addAll(defaultGrantedAuthorities(jwt));

    return authorities;
  }

  private Collection<GrantedAuthority> defaultGrantedAuthorities(Jwt jwt) {
    return Optional.ofNullable(defaultAuthoritiesConverter.convert(jwt))
        .orElse(emptySet());
  }


  @SuppressWarnings("unchecked")
  private List<String> realmRoles(Jwt jwt) {
    return Optional.ofNullable(jwt.getClaimAsMap(CLAIM_REALM_ACCESS))
        .map(realmAccess -> (List<String>) realmAccess.get(ROLES))
        .orElse(emptyList());
  }

  @SuppressWarnings("unchecked")
  private List<String> clientRoles(Jwt jwt, String clientId) {
    if (ObjectUtils.isEmpty(clientId)) {
      return emptyList();
    }

    return Optional.ofNullable(jwt.getClaimAsMap(RESOURCE_ACCESS))
        .map(resourceAccess -> (Map<String, List<String>>) resourceAccess.get(clientId))
        .map(clientAccess -> clientAccess.get(ROLES)
        .stream().map(roles -> ROLE_PREFIX + roles).toList())
        .orElse(emptyList());
  }

}
