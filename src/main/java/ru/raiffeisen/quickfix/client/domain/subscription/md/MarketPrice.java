package ru.raiffeisen.quickfix.client.domain.subscription.md;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Data
public final class MarketPrice {
    public static final MarketPrice ZERO = MarketPrice.builder().value(0.0d).build();

    private final double value;

    private final double forwardPoints;

    private MarketPrice(double value, double forwardPoints) {
        this.value = value;
        this.forwardPoints = forwardPoints;
    }
}
