package ru.raiffeisen.quickfix.client.domain.subscription.md;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum TradeSide {
    BUY(1) {
        @Override
        public TradeSide getOpposite() {
            return SELL;
        }

        @Override
        public String getMDEntryType() {
            return "1";
        }

        @Override
        public String getOrderSide() {
            return "1";
        }
    },
    SELL(2) {
        @Override
        public TradeSide getOpposite() {
            return BUY;
        }

        @Override
        public String getMDEntryType() {
            return "0";
        }

        @Override
        public String getOrderSide() {
            return "2";
        }
    };

    private static final Logger log = LoggerFactory.getLogger(TradeSide.class);

    private static final Map<String, TradeSide> mdEntryTypeToTradeSideMap = new HashMap<>(TradeSide.values().length);
    private static final Map<String, TradeSide> orderSideToTradeSideMap = new HashMap<>(TradeSide.values().length);
    private static final Map<Integer, TradeSide> indexToTradeSideMap = new HashMap<>(TradeSide.values().length);

    static {
        Arrays.stream(TradeSide.values()).forEach(item -> {
            mdEntryTypeToTradeSideMap.put(item.getMDEntryType(), item);
            orderSideToTradeSideMap.put(item.getOrderSide(), item);
            indexToTradeSideMap.put(item.getIndex(), item);
        });
    }

    private final int index;

    TradeSide(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public boolean isBuy() {
        return BUY == this;
    }

    public boolean isSell() {
        return SELL == this;
    }

    public abstract TradeSide getOpposite();

    public abstract String getMDEntryType();

    public abstract String getOrderSide();

    public static TradeSide ofName(String name) {
        if (name == null) {
            return null;
        }
        String src = name.toUpperCase();
        try {
            return TradeSide.valueOf(src);
        } catch (IllegalArgumentException e) {
            log.warn("Unable to find '{}' element by name '{}'", TradeSide.class.getSimpleName(), name, e);
            return null;
        }
    }

    public static TradeSide forMDEntryType(char mdEntryType) {
        return mdEntryTypeToTradeSideMap.get(String.valueOf(mdEntryType));
    }

    public static TradeSide forOrderSide(String orderSide) {
        if (orderSide == null) return null;
        return orderSideToTradeSideMap.get(orderSide);
    }

    @SuppressWarnings("unused")
    public static Optional<TradeSide> forIndex(int index) {
        if (index <= 0) return Optional.empty();
        return Optional.ofNullable(indexToTradeSideMap.get(index));
    }

    @SuppressWarnings("unused")
    public static TradeSide forIndex(int index, TradeSide fallback) {
        if (index <= 0) return fallback;
        return indexToTradeSideMap.getOrDefault(index, fallback);
    }
}
