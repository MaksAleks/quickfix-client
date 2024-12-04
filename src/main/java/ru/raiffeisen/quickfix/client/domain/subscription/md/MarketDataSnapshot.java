package ru.raiffeisen.quickfix.client.domain.subscription.md;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;

@Data
@Accessors(chain = true, fluent = true)
public final class MarketDataSnapshot {

    @NonNull
    private List<MarketDataParticle> particles;

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final Comparator<MarketDataParticle> COMPARATOR = Comparator.comparingLong(MarketDataParticle::size);

    public MarketDataSnapshot(@NonNull List<MarketDataParticle> particles) {
        particles.sort(COMPARATOR);
    }

    public boolean isEmpty() {
        return particles.isEmpty();
    }
}
