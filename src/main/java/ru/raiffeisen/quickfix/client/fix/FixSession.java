package ru.raiffeisen.quickfix.client.fix;

import quickfix.SessionID;
import quickfix.SessionSettings;

public record FixSession(
        String client,
        SessionID session,
        SessionSettings settings
) {
}
