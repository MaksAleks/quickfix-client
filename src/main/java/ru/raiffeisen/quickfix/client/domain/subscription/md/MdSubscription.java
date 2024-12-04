package ru.raiffeisen.quickfix.client.domain.subscription.md;

public record MdSubscription(
        String client,
        String id,
        String instrument
) { }
