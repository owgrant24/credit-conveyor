package com.example.deal.service;

import com.example.deal.db.entity.Application;
import com.example.deal.db.entity.Client;
import com.example.deal.db.entity.Credit;
import com.example.deal.db.entity.StatusHistory;
import com.example.deal.db.enums.ApplicationStatus;
import com.example.deal.db.enums.ChangeType;
import com.example.deal.db.enums.CreditStatus;
import com.example.deal.db.repository.ApplicationRepository;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final ApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;

    public List<LoanOfferDTO> application(LoanApplicationRequestDTO request) {
        Client client = createClient(request);
        Client savedClient = clientRepository.save(client);

        Application application = createApplication(savedClient);
        Application savedApplication = applicationRepository.save(application);

        List<LoanOfferDTO> offers = conveyorService.offers(request);
        offers.forEach(offer -> offer.setApplicationId(savedApplication.getId()));

        return offers;
    }

    public void offer(LoanOfferDTO request) {
        Application application = applicationRepository.getReferenceById(request.getApplicationId());
        application.setAppliedOffer(request);
        updateStatus(application, APPROVED, MANUAL);

        applicationRepository.save(application);
    }

    public void calculate(String id, FinishRegistrationRequestDTO request) {
        UUID applicationId = UUID.fromString(id);
        Application application = applicationRepository.getReferenceById(applicationId);
        ScoringDataDTO scoringData = dealMapper.map(request, application);

        CreditDTO creditDTO = conveyorService.calculation(scoringData);
        Credit credit = dealMapper.map(creditDTO);
        credit.setStatus(CreditStatus.CALCULATED);
        Credit savedCredit = creditRepository.save(credit);

        application.setCredit(savedCredit);
        updateStatus(application, CC_APPROVED, AUTOMATIC);
        Application savedApplication = applicationRepository.save(application);

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

    public void updateStatus(Application application, ApplicationStatus status, ChangeType changeType) {
        List<StatusHistory> statusHistory = updateStatusHistories(application.getStatusHistory(), status, changeType);
        application.setStatus(status);
        application.setStatusHistory(statusHistory);
    }

    private static List<StatusHistory> updateStatusHistories(List<StatusHistory> statusHistory,
                                                             ApplicationStatus status,
                                                             ChangeType changeType) {
        if (Objects.isNull(statusHistory)) {
            statusHistory = new ArrayList<>();
        }

        StatusHistory history = createStatusHistory(status, changeType);
        statusHistory.add(history);
        return statusHistory;
    }

    private static StatusHistory createStatusHistory(ApplicationStatus status, ChangeType changeType) {
        StatusHistory history = new StatusHistory();
        history.setStatus(status);
        history.setChangeType(changeType);
        history.setTime(Timestamp.from(Instant.now()));
        return history;
    }
}
