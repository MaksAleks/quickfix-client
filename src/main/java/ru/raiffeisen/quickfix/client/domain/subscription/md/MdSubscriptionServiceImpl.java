package ru.raiffeisen.quickfix.client.domain.subscription.md;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.StringField;
import quickfix.field.MDReqID;
import quickfix.field.MDUpdateType;
import quickfix.field.MarketDepth;
import quickfix.field.SubscriptionRequestType;
import quickfix.field.Symbol;
import quickfix.fix44.MarketDataRequest;
import quickfix.fix44.component.Instrument;
import ru.raiffeisen.quickfix.client.fix.ClientSessions;
import ru.raiffeisen.quickfix.client.fix.FixClient;
import ru.raiffeisen.quickfix.client.fix.FixSessionMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class MdSubscriptionServiceImpl implements MdSubscriptionService {

    private final MdSubscriptionRegistry registry;
    private final FixClient fixClient;
    private final FixSessionMapper fixSessionMapper;
    private final ClientSessions clientSessions;

    @Override
    public void subscribe(MdSubscription subscription, MdsConsumer consumer) {
        var fixSession = clientSessions.getSession(subscription.client());
        var sessionId = fixSessionMapper.getSessionId(fixSession);
        registry.registerSubscription(new MdSubscriptionRegistry.MdSubscriptionDescriptor(subscription, consumer));
        fixClient.send(toRequest(subscription), sessionId);
    }

    private MarketDataRequest toRequest(MdSubscription subscription) {
        var rq = new MarketDataRequest(
                new MDReqID(subscription.id()),
                new SubscriptionRequestType(SubscriptionRequestType.SNAPSHOT_UPDATES),
                new MarketDepth(0)
        );
        var noRelatedSym = new MarketDataRequest.NoRelatedSym();
        noRelatedSym.set(new Instrument(new Symbol(subscription.instrument())));
        rq.set(new MDUpdateType(MDUpdateType.FULL_REFRESH));
        rq.addGroup(noRelatedSym);
        rq.setString(10010, "N");
        return rq;
    }
}
