package com.example.application.integration.deal.config;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.netty.LogbookClientHandler;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class DealWebClientConfig {

    @Bean
    public WebClient conveyorWebClient(DealConfig config, Logbook logbook) {
        DealRestClientConfig clientConfig = config.getClient();


        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(clientConfig.getConnectTimeout()))
                .doOnConnected(connection -> connection
                        .addHandlerLast(new ReadTimeoutHandler(clientConfig.getReadTimeout()))
                        .addHandlerLast(new WriteTimeoutHandler(clientConfig.getWriteTimeout()))
                        .addHandlerLast(new LogbookClientHandler(logbook)));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .baseUrl(clientConfig.getUrl())
                .clientConnector(connector)
                .build();
    }
}
