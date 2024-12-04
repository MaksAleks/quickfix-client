package ru.raiffeisen.quickfix.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("quickfix.client")
public class QuickfixClientProperties {

    private String configFile;
}
