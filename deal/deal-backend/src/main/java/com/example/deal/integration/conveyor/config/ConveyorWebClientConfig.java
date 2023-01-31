package com.example.deal.integration.conveyor.config;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class ConveyorWebClientConfig {

    @Bean
    public WebClient conveyorWebClient(ConveyorConfig config) {
        ConveyorRestClientConfig clientConfig = config.getClient();

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(clientConfig.getConnectTimeout()))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(clientConfig.getReadTimeout()))
                        .addHandlerLast(new WriteTimeoutHandler(clientConfig.getWriteTimeout())));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .baseUrl(clientConfig.getUrl())
                .clientConnector(connector)
                .build();
    }
}
