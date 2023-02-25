package com.exceptionhandling.handlers;


import com.exceptionhandling.entities.Person;
import com.exceptionhandling.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class PersonHandler {

  private final PersonService personService;

  @NonNull
  public Mono<ServerResponse> getAll(ServerRequest req){

    return ok().contentType(MediaType.TEXT_EVENT_STREAM)
               .body(personService.testFlux(), Person.class);
  }

  @NonNull
  public Mono<ServerResponse> getAllPerProxy(ServerRequest req){

    return ok().contentType(MediaType.TEXT_EVENT_STREAM)
        .body(personService.getAllPerProxy(), Person.class);
  }

}
