package ru.raiffeisen.quickfix.client.fix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.SessionID;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
public class FixSessionMapperImpl implements FixSessionMapper {

    private final ConcurrentMap<FixSessionId, SessionID> map = new ConcurrentHashMap<>();

    @Override
    public void register(FixSessionId fixSessionId, SessionID sessionId) {
        map.computeIfAbsent(fixSessionId, k -> sessionId);
    }

    @Override
    public void unregister(FixSessionId fixSessionId) {
        map.remove(fixSessionId);
    }


    @Override
    public SessionID getSessionId(FixSessionId fixSessionId) {
        return map.get(fixSessionId);
    }
}
