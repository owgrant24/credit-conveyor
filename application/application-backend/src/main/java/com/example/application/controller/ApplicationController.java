package com.example.application.controller;

import com.example.application.api.ApplicationApi;
import com.example.application.dto.LoanApplicationRequestDTO;
import com.example.application.dto.LoanOfferDTO;
import com.example.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController implements ApplicationApi {

    private final ApplicationService applicationService;

    @Override
    public List<LoanOfferDTO> createApplication(LoanApplicationRequestDTO loanApplicationRequestDTO,
                                                HttpHeaders headers) {
        return applicationService.createLoanApplication(loanApplicationRequestDTO);
    }

    @Override
    public void applyOffer(LoanOfferDTO loanOfferDTO, HttpHeaders headers) {
        applicationService.applyOffer(loanOfferDTO);
    }
}
