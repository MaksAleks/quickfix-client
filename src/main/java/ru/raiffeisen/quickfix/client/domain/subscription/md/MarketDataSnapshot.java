package ru.raiffeisen.quickfix.client.domain.subscription.md;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import java.security.SecureRandom;
import java.util.Comparator;
import java.util.List;

@Data
public class MarketDataSnapshot {

    private List<MarketDataParticle> particles;

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final Comparator<MarketDataParticle> COMPARATOR = Comparator.comparingLong(MarketDataParticle::size);

    public MarketDataSnapshot(@NonNull List<MarketDataParticle> particles) {
        particles.sort(COMPARATOR);
        this.particles = particles;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return particles.isEmpty();
    }
}
