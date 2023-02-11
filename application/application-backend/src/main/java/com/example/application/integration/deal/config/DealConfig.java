package com.example.application.integration.deal.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "integration.deal")
public class DealConfig {

    @NotNull
    @NestedConfigurationProperty
    private DealRestClientConfig client;
}
