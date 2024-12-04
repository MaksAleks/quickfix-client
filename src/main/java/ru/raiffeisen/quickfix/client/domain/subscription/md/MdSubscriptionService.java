package ru.raiffeisen.quickfix.client.domain.subscription.md;

public interface MdSubscriptionService {

    void subscribe(MdSubscription subscription, MdsConsumer mdsConsumer);
}
