package com.example.conveyor.controller;

import com.example.conveyor.api.ConveyorApi;
import com.example.conveyor.dto.request.LoanApplicationRequestDTO;
import com.example.conveyor.dto.request.ScoringDataDTO;
import com.example.conveyor.dto.response.CreditDTO;
import com.example.conveyor.dto.response.LoanOfferDTO;
import com.example.conveyor.service.ConveyorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConveyorController implements ConveyorApi {

    private final ConveyorService conveyorService;

    @Override
    public List<LoanOfferDTO> offers(LoanApplicationRequestDTO loanApplicationRequestDTO,
                                     HttpHeaders headersForAudit) {
        return conveyorService.offers(loanApplicationRequestDTO);
    }

    @Override
    public CreditDTO calculation(ScoringDataDTO scoringDataDTO,
                                 HttpHeaders headersForAudit) {
        return conveyorService.calculation(scoringDataDTO);
    }
}
