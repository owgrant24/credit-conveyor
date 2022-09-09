package com.example.conveyor.service;

import com.example.conveyor.dto.request.LoanApplicationRequestDTO;
import com.example.conveyor.dto.request.ScoringDataDTO;
import com.example.conveyor.dto.response.CreditDTO;
import com.example.conveyor.dto.response.LoanOfferDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConveyorService {

    public List<LoanOfferDTO> offers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        // todo:
        return List.of();
    }

    public CreditDTO calculation(ScoringDataDTO scoringDataDTO) {
        // todo:
        return null;
    }
}
