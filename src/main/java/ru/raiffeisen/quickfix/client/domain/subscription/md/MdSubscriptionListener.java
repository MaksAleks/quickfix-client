package ru.raiffeisen.quickfix.client.domain.subscription.md;

import quickfix.SessionID;
import quickfix.fix44.MarketDataRequestReject;
import quickfix.fix44.MarketDataSnapshotFullRefresh;

public interface MdSubscriptionListener {

    void onSnapshot(MarketDataSnapshotFullRefresh mds, SessionID sessionId);

    void onReject(MarketDataRequestReject requestReject, SessionID sessionId);
}
