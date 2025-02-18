package ru.raiffeisen.quickfix.client.fix;

import org.springframework.stereotype.Service;
import quickfix.fix44.Message;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FixClientImpl implements FixClient {

    private final Map<String, FixInitiator> initiators;

    public FixClientImpl(List<FixInitiator> initiators) {
        this.initiators = initiators.stream()
                .collect(Collectors.toMap(FixInitiator::clientId, Function.identity()));
    }

    @Override
    public void send(Message message, String clientId) {
        var initiator = getInitiator(clientId);
        initiator.send(message);
    }

    private FixInitiator getInitiator(String clientId) {
        if (!initiators.containsKey(clientId)) {
            throw new IllegalArgumentException("Initiator session not found: " + clientId);
        }
        return initiators.get(clientId);
    }
}
