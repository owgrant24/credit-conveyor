package com.example.deal.integration.conveyor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "integration.conveyor")
public class ConveyorConfig {

    @NotNull
    @NestedConfigurationProperty
    private ConveyorRestClientConfig client;
}
