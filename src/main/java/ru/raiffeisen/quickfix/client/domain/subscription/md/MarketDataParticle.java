package ru.raiffeisen.quickfix.client.domain.subscription.md;

import java.util.Map;

public record MarketDataParticle(
        long size,
        Map<TradeSide, MarketPrice> prices
) { }
