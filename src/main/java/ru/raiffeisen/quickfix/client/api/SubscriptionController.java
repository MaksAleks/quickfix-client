package ru.raiffeisen.quickfix.client.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ru.raiffeisen.quickfix.client.domain.subscription.md.MarketDataSnapshot;
import ru.raiffeisen.quickfix.client.domain.subscription.md.MdSubscription;
import ru.raiffeisen.quickfix.client.domain.subscription.md.MdSubscriptionService;
import ru.raiffeisen.quickfix.client.domain.subscription.md.MdsConsumer;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final ConcurrentMap<String, SseEmitter> sse = new ConcurrentHashMap<>();
    private final MdSubscriptionService service;

    @PostMapping(value = "/md", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public void subscribeMd(@RequestBody MdSubscription subscription) {
        log.info("Subscribing to mds {}", subscription);
        sse.computeIfAbsent(subscription.id(), k -> {
            var emitter = new SseEmitter();
            service.subscribe(subscription, new MdsConsumerImpl(emitter));
            return emitter;
        });
    }

    @RequiredArgsConstructor
    private class MdsConsumerImpl implements MdsConsumer {

        private final SseEmitter emitter;

        @Override
        public void onSnapshot(MarketDataSnapshot mds) {
            try {
                emitter.send(mds);
            } catch (IOException e) {
                log.error("Error while emitting sse", e);
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onReject(String id, String reason) {
            try {
                emitter.completeWithError(new RuntimeException(reason));
                sse.remove(id);
            } catch (RuntimeException e) {
                log.error("Error while emitting sse", e);
                throw new RuntimeException(e);
            }
        }
    }
}
