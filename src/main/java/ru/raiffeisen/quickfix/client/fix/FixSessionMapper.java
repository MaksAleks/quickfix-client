package ru.raiffeisen.quickfix.client.fix;

import quickfix.SessionID;

public interface FixSessionMapper {

    void register(FixSessionId fixSessionId, SessionID sessionId);

    void unregister(FixSessionId fixSessionId);

    SessionID getSessionId(FixSessionId fixSessionId);
}
