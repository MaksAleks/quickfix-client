package ru.raiffeisen.quickfix.client.domain.subscription.md;

public interface MdsConsumer {

    void onSnapshot(MarketDataSnapshot mds);

    void onReject(String id, String reason);
}
