package com.monoflux.entities;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

@RequiredArgsConstructor
public class DemoPublisher implements Publisher<Integer> {

  private final List<Integer> list;


  @Override
  public void subscribe(Subscriber<? super Integer> subscriber) {
    Subscription demoSubscription = new DemoSubscription();
    subscriber.onSubscribe(demoSubscription);
  }
}
