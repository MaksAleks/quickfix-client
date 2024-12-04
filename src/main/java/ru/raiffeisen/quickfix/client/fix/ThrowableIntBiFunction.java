package ru.raiffeisen.quickfix.client.fix;

@FunctionalInterface
public interface ThrowableIntBiFunction<T, R> {

    R apply(int first, T throwable) throws Throwable;
}
