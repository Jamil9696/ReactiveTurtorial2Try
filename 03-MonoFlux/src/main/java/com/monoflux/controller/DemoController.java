package com.monoflux.controller;

import com.monoflux.entities.DemoPublisher;
import com.monoflux.entities.DemoSubscriber;
import io.r2dbc.spi.Parameter;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class DemoController {

  /* In the last Turtorials ( 01-02 ) we had used Flux<Person> as return value and it was Spring that was creating behind the scene a subscriber for our publisher (Flux).
     We only had to create a publisher while using router functionality or when we are returning from a method.

     Publisher and Subscriber communication
     /------------/                         /-------------/
     | Publisher  | <-----Subscription----- | Subscriber |
     |____________|                         |____________|
          | <----------subscribe()---------------- |
          | -----------Subsription --------------> |
          | <----------sub.onRequest(1)----------- |
          | -----------subscriber.onNext(x)------> |

      IMPORTANT!!!  Reactive and / or non reactive approach ?
      If we want to have reactive, we need to implement it on every layer even on the databases.
      Otherwise, you are not benefit from the reactive pattern. If one component is non reactive, another service will have to
      wait for it which will entirely block the reactive flow !!!!




   */
  @GetMapping("/demo")
  public void demo(){

    // static way of defining flux
    var f1 = Flux.just(1,2,3,4);
    var f2 = Flux.fromStream(Stream.of(1,2,3,4));
    Publisher<Integer> f3 = Flux.fromIterable(Set.of(1,2,3,4));
    var m1 = Mono.just(1);

   //  f1.doOnNext(System.out::println); // when we are not returning from the controller or use the router funcction, spring boot will not create
                                       // a subscriber. Without a subscriber nothing will happen
   //  f1.doOnNext(System.out::println)
   //    .subscribe(System.out::println);

    DemoPublisher customPublisher = new DemoPublisher(List.of(1,2,3,4,5));
    DemoSubscriber customSubscriber = new DemoSubscriber();

    customPublisher.subscribe(customSubscriber);

    // to define a flux dynamically through a sink, we can use the create-function
    // we can use a sink to schedule events in unlimited Stream in a pipeline
    Flux<String> f4 = Flux.create(s -> {
      for (int i = 0; i < 10; i++){
        // do sth
        s.next(UUID.randomUUID().toString());
      }
    });

    f4.log()                           // will show you which methods of the Publisher and Subscriber interface are called
      .subscribe(System.out::println); // calls request but in an unbounded way.
                                       // The subscriber will request everything the publisher has and
                                       // the publisher will provide everything he has and ends by calling the onComplete method.
                                       // This is not Backpressure because the subscriber would have request and get only a few events and
                                       // then requests further to get a new amount of events
  }
}
