package ru.raiffeisen.quickfix.client.fix;

@FunctionalInterface
public interface ThrowableFunction<T, R> {

    R apply(T throwable) throws Throwable;
}
