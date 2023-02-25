package com.proxyapplication.service;


import com.proxyapplication.entities.Person;
import com.proxyapplication.repository.PersonRepository;
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
    p1.setName("T_P1");
    p1.setAge(20);

    var p2 = new Person();
    p2.setName("T_P2");
    p2.setAge(21);

    var p3 = new Person();
    p3.setName("T_P3");
    p3.setAge(24);

    var p4 = new Person();

    return Flux.fromStream(Stream.of(p1, p4, p2,p3)).delayElements(Duration.ofSeconds(2));

  }


}
