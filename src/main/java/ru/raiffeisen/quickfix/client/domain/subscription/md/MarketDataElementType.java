package ru.raiffeisen.quickfix.client.domain.subscription.md;

import lombok.Getter;

@Getter
public enum MarketDataElementType {
    IMMEDIATE(1),
    FIXED_PRICE(2);

    private final int index;

    MarketDataElementType(int index) {
        this.index = index;
    }

}
