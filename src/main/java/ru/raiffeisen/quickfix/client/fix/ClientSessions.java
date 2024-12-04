package ru.raiffeisen.quickfix.client.fix;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties("fix")
public class ClientSessions {

    private List<ClientSession> sessions;

    public FixSessionId getSession(String client) {
        return sessions.stream()
                .filter(clientSession -> clientSession.getClientId().equals(client))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Session not found: [clientId: %s]".formatted(client)))
                .getFixSessionId();
    }

    @Data
    public static class ClientSession {
        private String clientId;
        private String sender;
        private String target;

        public FixSessionId getFixSessionId() {
            return new FixSessionId(sender, target);
        }
    }
}
