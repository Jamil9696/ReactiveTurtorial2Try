package com.essentialofpublisher.controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class DemoController {

  /* ============ Common mistakes
     When we work with reactive, we will literally have to implement everything in a functional way
     One of the common mistakes, that we can also see in case of Java Stream API for example,
     is that approach to try to take out specific elements from the pipeline and process them somewhere else
     and that is something you should try to avoid. It doesn't make sense to use a reactive approach if you take
     out the elements from a pipeline and process them iteratively. Don't use blocking function. when you want to
     take an element out of the pipeline, you have to block the thread. Therefore, do avoid as much as possible
     everything that is blocking. When a reactive method of a flux return something else than a publisher itself than it is a blocking method
   */


  @GetMapping(value = "/demo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> demo(){
    // we define endpoints we don't need to use subscribers because Spring boot will automatically attach a subscriber to our publisher
    // and provide the events when a client is sending a request to our endpoint. That's why it is enough to just define the publisher f1
    Flux<String> f1 = Flux.just("AAB", "AB", "CC", "DDDDD");

    return f1
           .log()
          .filter(s -> s.length() % 2 == 0);
       //    .distinct(); // will remove equal values
       //    .distinctUntilChanged(); // will remove only equal values that are listed one after the other
       //    .map(s -> s.length());   // will transform the flux of Strings into a flux of integers
       //    .flatMap(s -> Flux.just(s.split("")); //allows you to change, to extract somehow values and put them on the flux itself. For example changing a flux of strings into a flux of its characters
       //
    /* log function will print the communication between publisher and observer
     2023-02-26 15:31:48.249  INFO 15464 --- [ctor-http-nio-2] reactor.Flux.Array.1                     : | onSubscribe([Synchronous Fuseable] FluxArray.ArrayConditionalSubscription)
     2023-02-26 15:31:48.250  INFO 15464 --- [ctor-http-nio-2] reactor.Flux.Array.1                     : | request(1)    // request one
     2023-02-26 15:31:48.251  INFO 15464 --- [ctor-http-nio-2] reactor.Flux.Array.1                     : | onNext(AAA)   // but we see two values but only "AB" has an even size. therefore, the subscriber
     2023-02-26 15:31:48.251  INFO 15464 --- [ctor-http-nio-2] reactor.Flux.Array.1                     : | onNext(AB)    // has received 1 event
     2023-02-26 15:31:48.269  INFO 15464 --- [ctor-http-nio-2] reactor.Flux.Array.1                     : | request(31)   // Why request(31)? Because the observer sees that it could requests more values at the same time than before
     2023-02-26 15:31:48.269  INFO 15464 --- [ctor-http-nio-2] reactor.Flux.Array.1                     : | onNext(CC)
     2023-02-26 15:31:48.270  INFO 15464 --- [ctor-http-nio-2] reactor.Flux.Array.1                     : | onNext(DDDDD)
     2023-02-26 15:31:48.270  INFO 15464 --- [ctor-http-nio-2] reactor.Flux.Array.1                     : | onComplete()
     */

  }
}
