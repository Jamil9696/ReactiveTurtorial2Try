package com.sswithreactiveapp.controller;

import com.sswithreactiveapp.entities.Person;
import com.sswithreactiveapp.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "api/v1/person", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PersonController {

  private final PersonService personService;


  @GetMapping(path = "/get-all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @PreAuthorize("hasRole('Tester')")
  public Flux<Person> getPerson(){

    return personService.getAllPersons();

  }

}
