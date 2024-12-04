package ru.raiffeisen.quickfix.client.fix;

public record FixSessionId(
        String sender,
        String target
) {
}
