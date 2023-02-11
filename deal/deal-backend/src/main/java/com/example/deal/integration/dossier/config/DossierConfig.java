package com.example.deal.integration.dossier.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "integration.dossier")
public class DossierConfig {
    @NotEmpty
    private List<String> bootstrapServers;
    @NotNull
    @NestedConfigurationProperty
    private TopicConfig topic;

    public KafkaProperties toKafkaProperties() {
        KafkaProperties kafkaProperties = new KafkaProperties();
        kafkaProperties.setBootstrapServers(bootstrapServers);
        return kafkaProperties;
    }

    @Getter
    @Setter
    @Validated
    public static class TopicConfig {
        @NotBlank
        private String finishRegistration;
        @NotBlank
        private String createDocument;
        @NotBlank
        private String sendDocument;
        @NotBlank
        private String sendSes;
        @NotBlank
        private String creditIssued;
        @NotBlank
        private String applicationDenied;
    }
}
