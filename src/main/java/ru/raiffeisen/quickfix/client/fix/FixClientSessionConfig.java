package ru.raiffeisen.quickfix.client.fix;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import quickfix.ConfigError;
import quickfix.SessionID;
import quickfix.SessionSettings;

import java.util.List;

@Data
@Component
@ConfigurationProperties("fix.client")
public class FixClientSessionConfig {

    private String configFile;
    private List<ClientSessionConfig> sessions;

    public List<FixSession> getFixSessions() {
        return sessions.stream()
                .map(this::getClientSession)
                .toList();
    }

    private FixSession getClientSession(ClientSessionConfig config) {
        try {
            var settings = new SessionSettings(configFile);
            var sessionId = new SessionID(
                    settings.getString(SessionSettings.BEGINSTRING),
                    config.sender,
                    config.target);
            settings.setString(SessionSettings.SENDERCOMPID, config.sender);
            settings.setString(SessionSettings.TARGETCOMPID, config.target);
            return new FixSession(config.clientId, sessionId, settings);
        } catch (ConfigError e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    static class ClientSessionConfig {
        private String clientId;
        private String sender;
        private String target;
        private String password;
    }
}
