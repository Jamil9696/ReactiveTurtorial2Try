package com.exposingendpoint.handlers;

import com.exposingendpoint.entities.Person;
import com.exposingendpoint.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class PersonHandler {

  private final PersonService personService;

  public Mono<ServerResponse> getAll(ServerRequest req){

    return ok().contentType(MediaType.TEXT_EVENT_STREAM)
               .body(personService.testFlux(), Person.class);
  }
}
