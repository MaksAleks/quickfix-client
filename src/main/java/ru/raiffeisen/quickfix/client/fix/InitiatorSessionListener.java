package ru.raiffeisen.quickfix.client.fix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.Application;
import quickfix.Message;
import quickfix.SessionID;
import quickfix.fix44.MarketDataRequestReject;
import quickfix.fix44.MarketDataSnapshotFullRefresh;
import ru.raiffeisen.quickfix.client.domain.subscription.md.MdSubscriptionListener;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitiatorSessionListener implements Application {

    private final MdSubscriptionListener mdsListener;
    private final FixSessionMapper sessionMapper;

    @Override
    public void onCreate(SessionID sessionId) {
        log.info("InitiatorSessionListener onCreate " + sessionId);
        sessionMapper.register(new FixSessionId(sessionId.getSenderCompID(), sessionId.getTargetCompID()), sessionId);
    }

    @Override
    public void onLogon(SessionID sessionId) {
        log.info("InitiatorSessionListener onLogon " + sessionId);
    }

    @Override
    public void onLogout(SessionID sessionId) {
        log.info("InitiatorSessionListener onLogout " + sessionId);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        log.info("InitiatorSessionListener toAdmin " + sessionId);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) {
        log.info("InitiatorSessionListener fromAdmin " + sessionId);
    }

    @Override
    public void toApp(Message message, SessionID sessionId) {
        log.info("InitiatorSessionListener toApp " + sessionId);
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) {
        log.info("InitiatorSessionListener fromApp " + sessionId);
        if (message instanceof MarketDataSnapshotFullRefresh mds) {
            mdsListener.onSnapshot(mds, sessionId);
        } else if (message instanceof MarketDataRequestReject requestReject) {
            mdsListener.onReject(requestReject, sessionId);
        }
    }
}
