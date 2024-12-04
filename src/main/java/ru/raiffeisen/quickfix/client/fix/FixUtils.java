package ru.raiffeisen.quickfix.client.fix;

import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.Message;

import java.util.Optional;

public class FixUtils {

    public static <R, M extends Message> R getField(M message, ThrowableFunction<M, R> getter) {
        try {
            return getter.apply(message);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <R, G extends Group> R getField(G group, ThrowableFunction<G, R> getter) {
        try {
            return getter.apply(group);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <R, G extends Group> Optional<R> getFieldOpt(G group, ThrowableFunction<G, R> getter) {
        try {
            return Optional.of(getter.apply(group));
        } catch (FieldNotFound ex) {
            return Optional.empty();
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public static <M extends Message, G extends Group> G getGroup(M message, G group, int idx) {
        try {
            return (G) message.getGroup(idx, group);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }
}
