package ru.raiffeisen.quickfix.client.domain.subscription.md;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.field.PriceDelta;
import quickfix.fix44.MarketDataSnapshotFullRefresh;
import quickfix.fix44.MarketDataSnapshotFullRefresh.NoMDEntries;
import ru.raiffeisen.quickfix.client.fix.FixUtils;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class MarketDataSnapshotConverter {

    public MarketDataSnapshot convert(MarketDataSnapshotFullRefresh marketDataSnapshot) {
        var mdItems = toMarketDataParticles(marketDataSnapshot);
        return new MarketDataSnapshot(mdItems);
    }

    private List<MarketDataParticle> toMarketDataParticles(
            @NonNull MarketDataSnapshotFullRefresh marketDataSnapshot) {
        int entriesNumber = FixUtils.getField(marketDataSnapshot, MarketDataSnapshotFullRefresh::getNoMDEntries).getValue();
        var particles = new ArrayList<MarketDataParticle>(entriesNumber);
        var prices = new HashMap<Long, EnumMap<TradeSide, MarketPrice>>();
        for (int i = 0; i < entriesNumber; i++) {
            var entry = toMarketDataEntry(marketDataSnapshot, i + 1);
            prices.computeIfAbsent(entry.size(), size -> new EnumMap<>(TradeSide.class))
                    .put(entry.side(), MarketPrice.ZERO.equals(entry.price()) ? null : entry.price());
        }

        prices.forEach((size, particlePrices) -> {
            particles.add(new MarketDataParticle(size, particlePrices));
        });

        return particles;
    }

    private MarketDataEntry toMarketDataEntry(MarketDataSnapshotFullRefresh marketDataSnapshot, int index) {
        var noMDEntries = FixUtils.getGroup(marketDataSnapshot, new NoMDEntries(), index);
        var entryType = FixUtils.getField(noMDEntries, NoMDEntries::getMDEntryType).getValue();
        var tradeSide = TradeSide.forMDEntryType(entryType);
        var basePrice = FixUtils.getField(noMDEntries, NoMDEntries::getMDEntryPx).getValue();
        var forwardPoints = FixUtils.getFieldOpt(noMDEntries, NoMDEntries::getPriceDelta)
                .map(PriceDelta::getValue)
                .orElse(0.0);
        var marketPrice = MarketPrice.builder().value(basePrice).forwardPoints(forwardPoints).build();
        long size = (int) FixUtils.getField(noMDEntries, NoMDEntries::getMDEntrySize).getValue();
        return new MarketDataEntry(tradeSide, marketPrice, size);
    }
}
