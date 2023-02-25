package com.exposingendpoint.config;


import com.exposingendpoint.entities.Person;
import com.exposingendpoint.handlers.PersonHandler;
import com.exposingendpoint.service.PersonService;
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
public class ReactConfig {


  @Bean
  public RouterFunction<ServerResponse> configRouter(PersonService personService){
    return route()
        .GET("/person-by-route", req ->
            ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(personService.testFlux(), Person.class))
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> betterApproachToConfigRouter(PersonHandler personHandler) {
    return route()
        .GET("/person-by-better-route-approach", personHandler::getAll)
        .build();
  }
}
