package com.example.conveyor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    @Min(value = 0)
    private BigDecimal baseRate;
}
