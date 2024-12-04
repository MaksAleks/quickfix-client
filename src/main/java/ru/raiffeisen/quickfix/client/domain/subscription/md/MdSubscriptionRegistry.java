package ru.raiffeisen.quickfix.client.domain.subscription.md;

public interface MdSubscriptionRegistry {

    void registerSubscription(MdSubscriptionDescriptor subscription);


    record MdSubscriptionDescriptor(
            MdSubscription subscription,
            MdsConsumer consumer
    ) { }
}
