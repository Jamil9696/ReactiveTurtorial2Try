package com.exceptionhandling.config;

import com.exceptionhandling.entities.Person;
import com.exceptionhandling.handlers.PersonHandler;
import com.exceptionhandling.service.PersonService;
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

    /*
    http://localhost:9092/person-by-proxy          http://localhost:9093/person

    ----------Application 1 -------------          ---------------Application 2 -------------
    Routes -> Handler -> Service -> Proxy  ------> Routes -> Handler -> service -> repository

   */

  private final PersonHandler personHandler;

  @Bean
  public RouterFunction<ServerResponse> configRouter(PersonService personService){
    return route()
        .GET("/person-by-route", req ->
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
        .GET("/person-by-better-route-approach", personHandler::getAll)
        .build();
  }


}
