package com.example.deal.controller;

import com.example.deal.api.DealApi;
import com.example.deal.dto.request.FinishRegistrationRequestDTO;
import com.example.deal.dto.request.LoanApplicationRequestDTO;
import com.example.deal.dto.response.LoanOfferDTO;
import org.springframework.http.HttpHeaders;

import java.util.List;

public class DealController implements DealApi {
    @Override
    public List<LoanOfferDTO> application(LoanApplicationRequestDTO loanApplicationRequestDTO,
                                          HttpHeaders headers) {
        // TODO: implement
        return null;
    }

    @Override
    public void offer(LoanOfferDTO loanOfferDTO,
                      HttpHeaders headers) {
        // TODO: implement
    }

    @Override
    public void calculate(String applicationId,
                          FinishRegistrationRequestDTO finishRegistrationRequestDTO,
                          HttpHeaders headers) {
        // TODO: implement
    }
}
