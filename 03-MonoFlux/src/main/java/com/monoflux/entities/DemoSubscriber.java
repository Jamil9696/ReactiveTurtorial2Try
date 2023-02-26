package com.monoflux.entities;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DemoSubscriber implements Subscriber<Integer> {

  /* The subscription is the link between the publisher and the subscriber.
     the onSubscribe Method is used by the subscriber to get a subscription from a publisher
     and communicate through the subscription with the publisher. The subscriber communicates with the publisher by asking the püublisher
     to get values from it. So the publisher is not pushing values to the subscription but the subscriber requests values through the subscription (back pressure).
     Back pressure means that the subscriber only gets that amount of events that they can process and
     the publisher can not know which amount of events the consumer can process. that's why the consumer instead of the publisher always request data

   */

  private Subscription subscription;
  @Override
  public void onSubscribe(Subscription subscription) {
    this.subscription = subscription;
    subscription.request(1);

  }
  /* onNext(T var1) is called by the publisher when the subscriber requests values */
  @Override
  public void onNext(Integer integer) {
    subscription.request(1);
  }

  /* this method is called by the publisher when an error occurred on the side of the publisher
      On this way the publisher notify his consumer that something happened
  */
  @Override
  public void onError(Throwable throwable) {

  }
  /* onComplete is also called by the publisher to tell the consumer that he has no more values*/
  @Override
  public void onComplete() {

  }
}
