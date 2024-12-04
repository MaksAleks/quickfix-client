package ru.raiffeisen.quickfix.client.domain.subscription.md;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.SessionID;
import quickfix.fix44.MarketDataRequestReject;
import quickfix.fix44.MarketDataSnapshotFullRefresh;
import ru.raiffeisen.quickfix.client.fix.FixUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class MdSubscriptionListenerImpl implements MdSubscriptionListener, MdSubscriptionRegistry {

    private final MarketDataSnapshotConverter converter;
    private final ConcurrentMap<String, MdSubscriptionDescriptor> subscriptions = new ConcurrentHashMap<>();

    @Override
    public void registerSubscription(MdSubscriptionDescriptor descriptor) {
        subscriptions.computeIfAbsent(descriptor.subscription().id(), key -> descriptor);
    }

    @Override
    public void onSnapshot(MarketDataSnapshotFullRefresh mds, SessionID sessionId) {
        var instrument = FixUtils.getField(mds, MarketDataSnapshotFullRefresh::getSymbol).getValue();
        var id = FixUtils.getField(mds, MarketDataSnapshotFullRefresh::getMDReqID).getValue();
        var subscription = subscriptions.get(id);
        if (subscription == null) {
            log.error("Got mds for instrument {}, but no subscription found", instrument);
            return;
        }
        try {
            subscription.consumer().onSnapshot(converter.convert(mds));
        } catch (RuntimeException e) {
            log.error("Unexpected exception thrown while processing new snapshot for instrument {}", instrument, e);
        }
    }

    @Override
    public void onReject(MarketDataRequestReject requestReject, SessionID sessionId) {
        var id = FixUtils.getField(requestReject, MarketDataRequestReject::getMDReqID).getValue();
        var reason = FixUtils.getField(requestReject, MarketDataRequestReject::getText).getValue();
        log.error("MD subscription {} rejected. Reason: {}", id, reason);
        subscriptions.remove(id).consumer().onReject(id, reason);
    }
}
