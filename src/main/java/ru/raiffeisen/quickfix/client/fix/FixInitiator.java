package ru.raiffeisen.quickfix.client.fix;

import quickfix.fix44.Message;

public interface FixInitiator {

    void send(Message message);

    String clientId();
}
