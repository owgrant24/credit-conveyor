package com.example.conveyor.service;

import com.example.conveyor.dto.request.LoanApplicationRequestDTO;
import com.example.conveyor.dto.request.ScoringDataDTO;
import com.example.conveyor.dto.response.CreditDTO;
import com.example.conveyor.dto.response.LoanOfferDTO;
import com.example.conveyor.mapper.ConveyorMapper;
import com.example.conveyor.model.CreditInfo;
import com.example.conveyor.model.LoanInfo;
import com.example.conveyor.model.LoanOffer;
import com.example.conveyor.model.ScoringInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConveyorService {

    private final LoanOfferService loanOfferService;
    private final ScoringService scoringService;

    private final ConveyorMapper conveyorMapper;

    public List<LoanOfferDTO> offers(LoanApplicationRequestDTO loanApplicationRequest) {
        LoanInfo loanInfo = conveyorMapper.mapRequest(loanApplicationRequest);
        List<LoanOffer> loanOffers = loanOfferService.createOffers(loanInfo);
        return conveyorMapper.mapResponse(loanOffers);
    }

    public CreditDTO calculation(ScoringDataDTO scoringDataDTO) {
        ScoringInfo scoringInfo = conveyorMapper.mapRequest(scoringDataDTO);
        CreditInfo creditInfo = scoringService.createCredit(scoringInfo);
        return conveyorMapper.mapResponse(creditInfo);
    }
}
