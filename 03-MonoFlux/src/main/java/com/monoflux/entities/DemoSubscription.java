package com.monoflux.entities;

import org.reactivestreams.Subscription;

public class DemoSubscription implements Subscription {


  // this method represent the backpressure principle, because the subscriber
  // would be able to requests as much data as it wants to have
  @Override
  public void request(long l) {

  }

  // will cancel the subsription
  @Override
  public void cancel() {

  }
}
