package ru.raiffeisen.quickfix.client.domain.subscription.md;

public record MarketDataEntry(
        TradeSide side,
        MarketPrice price,
        long size
) {
}
