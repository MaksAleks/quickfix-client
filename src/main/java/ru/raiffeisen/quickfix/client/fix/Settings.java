package ru.raiffeisen.quickfix.client.fix;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import quickfix.ConfigError;
import quickfix.SessionSettings;

import java.io.IOException;

@Slf4j
public class Settings {

    @Getter
    private final SessionSettings sessionSettings;

    public Settings(String configFileName) {
        log.info("Loading settings from {}", configFileName);
        var settingsResource = new ClassPathResource(configFileName);
        try (var settingsFile = settingsResource.getInputStream()) {
            sessionSettings = new SessionSettings(settingsFile);
        } catch (IOException | ConfigError e) {
            throw new RuntimeException(e);
        }
    }
}
