package com.exceptionhandling.proxy;

import com.exceptionhandling.entities.Person;
import com.exceptionhandling.exception.PersonRetrieveException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Flux;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class PersonProxy {

  private final WebClient webClient;

  public Flux<Person> getAllPerProxy(){
    return webClient
        .get()
        .uri("/get-test-data")
        .exchangeToFlux(response -> response.bodyToFlux(Person.class) )
                                           // will give you the possibility to specify sth that should happen instead in case an exception appears
                                           // there are three approaches to specify exception handling via by using webclient
                                           // In a real world exception, it would be better to not implement the logic of an error here for instance e -> Flux.empty() because normally
                                           // we would return more than an empty Flux. So always decouple the logic in a separate class

        //.onErrorResume(e -> Flux.empty());                                                // (1) or we could call another service as an alternative if the first service is not working
        //.onErrorResume(WebClientRequestException.class, e -> Flux.empty());               // (2) here we specify the type of exception (recommended way)
        //.onErrorResume(e -> e instanceof WebClientRequestException, e -> Flux.empty());   // (3) other way of specifying exceptions,
        //.onErrorResume(e -> e.getMessage.isEmpty(), e -> Flux.empty() );                  //     when for example we want check more than the type of the exception
        //.onErrorReturn( new Person() OR                                                   // returns an alternative person object but doesn't give you access to the exception (3 ways are possible)
        //                e -> e.getMessage == null, e -> do sth OR
        //                type of exception e, new Person );
        //.onErrorMap(e -> new PersonRetrieveException()) ;                                 // onErrorMap will convert an exception to another one. Usually we do it when we want to treat the exception somewhere else
        .doOnNext(p -> {
          if(p.getName() == null) throw new RuntimeException("No valid Person! This Person will be skipped");   // only for demonstration purpose. Never implement the logic here and never use a non-specific exception (not recommended)
        })                                                                                                      // We normally don't want to treat the technical exception (e) that happens here
        .onErrorContinue((e,o) -> log.error(e.getMessage()));                                                   // onErrorContinue If just an event but not the whole stream is wrong, what should happen?
                                                                                                                // If you consume an external service than you should never trust, that it handles things correctly

  }
}
