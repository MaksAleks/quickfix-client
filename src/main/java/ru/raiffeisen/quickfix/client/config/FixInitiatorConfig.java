package ru.raiffeisen.quickfix.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.DefaultMessageFactory;
import quickfix.fix44.MessageFactory;
import ru.raiffeisen.quickfix.client.fix.Settings;

@Configuration
public class FixInitiatorConfig {

    @Bean
    public Settings serverSettings(QuickfixClientProperties serverProperties) {
        return new Settings(serverProperties.getConfigFile());
    }

    @Bean
    public MessageFactory messageFactory() {
        return new MessageFactory();
    }
}
