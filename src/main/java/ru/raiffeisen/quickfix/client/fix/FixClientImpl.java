package ru.raiffeisen.quickfix.client.fix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;
import quickfix.ConfigError;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.ScreenLogFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.SocketInitiator;
import quickfix.fix44.Message;

@Slf4j
@Service
public class FixClientImpl implements FixClient, DisposableBean {

    private final SocketInitiator initiator;

    public FixClientImpl(Settings settings,
                         MessageFactory messageFactory,
                         InitiatorSessionListener listener) {
        try {
            FileStoreFactory storeFactory = new FileStoreFactory(settings.getSessionSettings());
            LogFactory logFactory = new ScreenLogFactory(settings.getSessionSettings());
            this.initiator = new SocketInitiator(
                    listener, storeFactory, settings.getSessionSettings(),
                    logFactory, messageFactory);
        } catch (ConfigError e) {
            throw new RuntimeException(e);
        }
    }

    void start() {
        try {
            initiator.start();
        } catch (ConfigError e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        initiator.stop();
    }

    @Override
    public void send(Message message, SessionID sessionId) {
        try {
            Session.sendToTarget(message, sessionId);
        } catch (SessionNotFound e) {
            throw new RuntimeException(e);
        }
    }
}
