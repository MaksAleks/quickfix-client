package ru.raiffeisen.quickfix.client.fix;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quickfix.ConfigError;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.ScreenLogFactory;
import quickfix.SocketInitiator;

@Service
@RequiredArgsConstructor
public class FixClientInitiatorFactory {

    private final InitiatorSessionListener listener;
    private final MessageFactory messageFactory;
    private final FixClientSessionConfig config;

    public FixInitiator createFor(FixSession session) {
        try {
            var settings = session.settings();
            FileStoreFactory storeFactory = new FileStoreFactory(settings);
            LogFactory logFactory = new ScreenLogFactory(settings);
            var initiator = new SocketInitiator(
                    listener, storeFactory, settings,
                    logFactory, messageFactory);
            return new FixClientInitiator(session, initiator);
        } catch (ConfigError e) {
            throw new RuntimeException(e);
        }
    }
}
