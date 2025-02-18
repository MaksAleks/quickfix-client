package ru.raiffeisen.quickfix.client.fix;

import lombok.RequiredArgsConstructor;
import quickfix.ConfigError;
import quickfix.Session;
import quickfix.SessionNotFound;
import quickfix.SocketInitiator;
import quickfix.fix44.Message;

@RequiredArgsConstructor
class FixClientInitiator implements FixInitiator {

    private final FixSession session;
    private final SocketInitiator initiator;

    void start() {
        try {
            initiator.start();
        } catch (ConfigError e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        initiator.stop();
    }

    @Override
    public void send(Message message) {
        try {
            Session.sendToTarget(message, session.session());
        } catch (SessionNotFound e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String clientId() {
        return session.client();
    }
}
