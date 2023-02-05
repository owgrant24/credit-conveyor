package com.example.application.integration.deal;

import com.example.application.dto.LoanApplicationRequestDTO;
import com.example.application.dto.LoanOfferDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealService {

    private static final String URL_DEAL_APPLICATION = "/deal/application";
    private static final String URL_DEAL_OFFER = "/deal/offer";

    private final WebClient dealWebClient;

    public List<LoanOfferDTO> application(LoanApplicationRequestDTO request) {
        return dealWebClient.post()
                .uri(URL_DEAL_APPLICATION)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(LoanOfferDTO.class)
                .collectList()
                .block();
    }

    public void applyOffer(LoanOfferDTO request) {
        dealWebClient.put()
                .uri(URL_DEAL_OFFER)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
