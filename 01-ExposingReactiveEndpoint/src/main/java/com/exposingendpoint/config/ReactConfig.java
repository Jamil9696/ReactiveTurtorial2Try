package com.exposingendpoint.config;


import com.exposingendpoint.entities.Person;
import com.exposingendpoint.handlers.PersonHandler;
import com.exposingendpoint.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@EnableR2dbcRepositories
@RequiredArgsConstructor
public class ReactConfig {


    /* One of the advantages of reactive applications is that they need less idle threads than non-reactive and are, therefore,
       more efficient than non-reactive application in terms of resource management
       applications ( idle threads will be created by the application and are managed by the JVM, the garbage collector for instance
       is running in such a thread. ). The second purpose of a reactive application is that it's possible to have an easy
       implementable event driven communication, which allows us to decouple the consumer from the producer of data.
       It's possible to have reactive and non-reative application talking to each other but in most cases, you won't be able
       to make the best out of it at least in terms of where it's possible to make events (here it's definitely better to have
       reactive application in both places (producer and consumer side) rather than having one reactive an one non-reactive application


       http communication via proxys
       http://localhost:9091/person-by-proxy                   http://localhost:9093/person-by-proxy

       ----------Application 1 -------------    will call      ---------------Application 2 (ProxyApplication) -------------
       Routes -> Handler -> Service -> Proxy     ------>       Routes -> Handler -> service -> repository

   */

  private final PersonHandler personHandler;


  // We can cconfigure endpoints at the ccontroller level or in a own Configuration class like here
  @Bean
  public RouterFunction<ServerResponse> configRouter(PersonService personService){
    return route()
        .GET("/person-by-route", req ->
            // bad practise
            ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(personService.testFlux(), Person.class))
        .build();
  }
  @Bean
  public RouterFunction<ServerResponse> router(){
    return route().GET("/person-by-proxy", personHandler::getAllPerProxy).build();
  }

  @Bean
  public RouterFunction<ServerResponse> betterApproachToConfigRouter() {
    return route()
        .GET("/person-by-better-route-approach", personHandler::getAll) // better approach by using handler
        .build();
  }


}
