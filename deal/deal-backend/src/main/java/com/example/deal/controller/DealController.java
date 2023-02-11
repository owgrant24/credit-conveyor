package com.example.deal.controller;

import com.example.deal.api.DealApi;
import com.example.deal.dto.request.FinishRegistrationRequestDTO;
import com.example.deal.dto.request.LoanApplicationRequestDTO;
import com.example.deal.dto.response.LoanOfferDTO;
import com.example.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DealController implements DealApi {

    private final DealService dealService;

    @Override
    public List<LoanOfferDTO> application(LoanApplicationRequestDTO loanApplicationRequestDTO,
                                          HttpHeaders headers) {
        return dealService.application(loanApplicationRequestDTO);
    }

    @Override
    public void offer(LoanOfferDTO loanOfferDTO,
                      HttpHeaders headers) {
        dealService.offer(loanOfferDTO);
    }

    @Override
    public void calculate(String applicationId,
                          FinishRegistrationRequestDTO finishRegistrationRequestDTO,
                          HttpHeaders headers) {
        dealService.calculate(applicationId, finishRegistrationRequestDTO);
    }
}
