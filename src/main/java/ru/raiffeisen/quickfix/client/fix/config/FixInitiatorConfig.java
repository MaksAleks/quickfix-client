package ru.raiffeisen.quickfix.client.fix.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.fix44.MessageFactory;
import ru.raiffeisen.quickfix.client.fix.FixClientInitiatorFactory;
import ru.raiffeisen.quickfix.client.fix.FixClientSessionConfig;
import ru.raiffeisen.quickfix.client.fix.FixInitiator;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FixInitiatorConfig {

    private final FixClientInitiatorFactory factory;
    private final FixClientSessionConfig sessionConfig;

    @Bean
    public List<FixInitiator> fixClientInitiators() {
        return sessionConfig.getFixSessions().stream()
                .map(factory::createFor)
                .toList();
    }

    @Bean
    public MessageFactory messageFactory() {
        return new MessageFactory();
    }
}
