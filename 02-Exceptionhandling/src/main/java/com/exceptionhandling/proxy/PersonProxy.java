package com.exceptionhandling.proxy;

import com.exceptionhandling.entities.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class PersonProxy {

  private final WebClient webClient;

  public Flux<Person> getAllPerProxy(){
    return webClient
        .get()
        .uri("/person")
        .exchangeToFlux(response -> response.bodyToFlux(Person.class) );
  }
}
