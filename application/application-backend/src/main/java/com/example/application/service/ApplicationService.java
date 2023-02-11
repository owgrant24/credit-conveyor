package com.example.application.service;

import com.example.application.dto.LoanApplicationRequestDTO;
import com.example.application.dto.LoanOfferDTO;
import com.example.application.integration.deal.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final PreScoringService preScoringService;
    private final DealService dealService;

    public List<LoanOfferDTO> createLoanApplication(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        preScoringService.validate(loanApplicationRequestDTO);
        return dealService.application(loanApplicationRequestDTO);
    }

    public void applyOffer(LoanOfferDTO loanOfferDTO) {
        dealService.applyOffer(loanOfferDTO);
    }
}
