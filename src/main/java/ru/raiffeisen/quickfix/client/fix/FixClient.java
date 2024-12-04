package ru.raiffeisen.quickfix.client.fix;

import quickfix.SessionID;
import quickfix.fix44.Message;

public interface FixClient {

    void send(Message message, SessionID sessionId);
}
