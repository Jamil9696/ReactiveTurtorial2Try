package com.exposingrefactoring.service;


import com.exposingrefactoring.entities.Person;
import com.exposingrefactoring.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@AllArgsConstructor
public class PersonService {

  private final PersonRepository personRepository;

  public Flux<Person> getAllPersons(){

    return personRepository.findAll().delayElements(Duration.ofSeconds(3));

  }

}
