package com.example.application.integration.deal.config;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DealRestClientConfig {
    @NotEmpty
    private String url;
    @Min(0)
    @NotNull
    private Integer connectTimeout;
    @Min(1)
    @NotNull
    private Integer readTimeout;
    @Min(1)
    @NotNull
    private Integer writeTimeout;
}
