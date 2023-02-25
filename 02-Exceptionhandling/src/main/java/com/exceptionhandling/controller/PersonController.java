package com.exceptionhandling.controller;

import com.exceptionhandling.entities.Person;
import com.exceptionhandling.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class PersonController {

  private final PersonService personService;


  @GetMapping(value = "/person", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Person> getPerson(){

    return personService.getAllPersons();

  }

  @GetMapping(value = "/person-per-proxy", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Person> getPersonViaProxy(){

    return personService.getAllPerProxy();

  }



}
