package com.exposingendpoint.service;


import com.exposingendpoint.entities.Person;
import com.exposingendpoint.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class PersonService {

  private final PersonRepository personRepository;

  public Flux<Person> getAllPersons(){

    return personRepository.findAll().delayElements(Duration.ofSeconds(3));
  }

  public Flux<Person> testFlux(){
    var p1 = new Person();
    p1.setName("P1");
    p1.setAge(20);

    var p2 = new Person();
    p2.setName("P2");
    p2.setAge(21);

    var p3 = new Person();
    p3.setName("P3");
    p3.setAge(24);

    return Flux.fromStream(Stream.of(p1,p2,p3)).delayElements(Duration.ofSeconds(2));

  }

}