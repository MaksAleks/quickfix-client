package ru.raiffeisen.quickfix.client.fix;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FixClientRunner implements ApplicationRunner {

    private final FixClientImpl fixClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting fix client");
        fixClient.start();
        log.info("Fix client has started");
    }
}
