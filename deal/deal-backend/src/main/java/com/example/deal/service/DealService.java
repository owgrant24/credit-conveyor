package com.example.deal.service;

import com.example.deal.db.entity.Application;
import com.example.deal.db.entity.Client;
import com.example.deal.db.entity.Credit;
import com.example.deal.db.enums.CreditStatus;
import com.example.deal.db.helper.ApplicationHelper;
import com.example.deal.db.repository.ClientRepository;
import com.example.deal.db.repository.CreditRepository;
import com.example.deal.dto.request.FinishRegistrationRequestDTO;
import com.example.deal.dto.request.LoanApplicationRequestDTO;
import com.example.deal.dto.request.ScoringDataDTO;
import com.example.deal.dto.response.CreditDTO;
import com.example.deal.dto.response.LoanOfferDTO;
import com.example.deal.integration.conveyor.ConveyorService;
import com.example.deal.mapper.DealMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.example.deal.db.enums.ApplicationStatus.APPROVED;
import static com.example.deal.db.enums.ApplicationStatus.CC_APPROVED;
import static com.example.deal.db.enums.ChangeType.AUTOMATIC;
import static com.example.deal.db.enums.ChangeType.MANUAL;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealMapper dealMapper;
    private final ClientRepository clientRepository;
    private final ConveyorService conveyorService;
    private final DocumentService documentService;
    private final ApplicationHelper applicationHelper;
    private final CreditRepository creditRepository;

    public List<LoanOfferDTO> application(LoanApplicationRequestDTO request) {
        Client client = createClient(request);
        Client savedClient = clientRepository.save(client);

        Application application = createApplication(savedClient);
        Application savedApplication = applicationHelper.save(application);

        List<LoanOfferDTO> offers = conveyorService.offers(request);
        offers.forEach(offer -> offer.setApplicationId(savedApplication.getId()));

        return offers;
    }

    public void offer(LoanOfferDTO request) {
        Application application = applicationHelper.getById(request.getApplicationId());
        application.setAppliedOffer(request);
        applicationHelper.saveAndUpdateStatus(application, APPROVED, MANUAL);
    }

    public void calculate(String id, FinishRegistrationRequestDTO request) {
        UUID applicationId = UUID.fromString(id);
        Application application = applicationHelper.getById(applicationId);
        ScoringDataDTO scoringData = dealMapper.map(request, application);

        CreditDTO creditDTO = conveyorService.calculation(scoringData);
        Credit credit = dealMapper.map(creditDTO);
        credit.setStatus(CreditStatus.CALCULATED);
        Credit savedCredit = creditRepository.save(credit);

        application.setCredit(savedCredit);
        Application savedApplication = applicationHelper.saveAndUpdateStatus(application, CC_APPROVED, AUTOMATIC);

        documentService.createDocuments(savedApplication);
    }

    private Client createClient(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        return dealMapper.map(loanApplicationRequestDTO);
    }

    private static Application createApplication(Client savedClient) {
        Application application = new Application();
        application.setClient(savedClient);
        return application;
    }
}
