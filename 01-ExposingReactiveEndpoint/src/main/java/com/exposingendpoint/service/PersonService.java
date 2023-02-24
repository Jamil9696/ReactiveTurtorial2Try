package com.exposingendpoint.service;


import com.exposingendpoint.entities.Person;
import com.exposingendpoint.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class PersonService {

  private final PersonRepository personRepository;

  public Flux<Person> getAllPersons(){

    personRepository.findAllById()
  }

}
