package com.example.deal.integration.conveyor;

import com.example.deal.dto.request.LoanApplicationRequestDTO;
import com.example.deal.dto.request.ScoringDataDTO;
import com.example.deal.dto.response.CreditDTO;
import com.example.deal.dto.response.LoanOfferDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConveyorService {
    private static final String URL_CONVEYOR_CALCULATION = "/conveyor/calculation";
    private static final String URL_CONVEYOR_OFFERS = "/conveyor/offers";
    private final WebClient conveyorWebClient;

    public List<LoanOfferDTO> offers(LoanApplicationRequestDTO request) {
        return conveyorWebClient.post()
                .uri(URL_CONVEYOR_OFFERS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(LoanOfferDTO.class)
                .collectList()
                .block();
    }

    public CreditDTO calculation(ScoringDataDTO scoringData) {
        return conveyorWebClient.post()
                .uri(URL_CONVEYOR_CALCULATION)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(scoringData)
                .retrieve()
                .bodyToMono(CreditDTO.class)
                .block();
    }
}
