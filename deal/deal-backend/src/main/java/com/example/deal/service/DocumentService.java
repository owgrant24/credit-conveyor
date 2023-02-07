package com.example.deal.service;

import com.example.deal.db.entity.Application;
import com.example.deal.db.entity.Credit;
import com.example.deal.db.enums.ApplicationStatus;
import com.example.deal.db.enums.CreditStatus;
import com.example.deal.db.repository.ApplicationRepository;
import com.example.deal.db.repository.CreditRepository;
import com.example.deal.dto.EmailMessage;
import com.example.deal.dto.Theme;
import com.example.deal.exception.DealException;
import com.example.deal.integration.dossier.DossierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static com.example.deal.db.enums.ApplicationStatus.CREDIT_ISSUED;
import static com.example.deal.db.enums.ChangeType.AUTOMATIC;
import static com.example.deal.db.enums.ChangeType.MANUAL;
import static com.example.deal.util.SesCodeGeneratorUtil.createSesCode;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DossierService dossierService;
    private final DealService dealService;
    private final ApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;

    public void createDocuments(Application application) {
        EmailMessage emailMessage = createEmailMessage(application, Theme.CREATE_DOCUMENT);
        dossierService.sendMessage(emailMessage);
    }

    public void send(UUID applicationId) {
        Application application = applicationRepository.getReferenceById(applicationId);
        EmailMessage emailMessage = createEmailMessage(application, Theme.SEND_DOCUMENT);
        dossierService.sendMessage(emailMessage);
    }

    public void sign(UUID applicationId) {
        Application application = applicationRepository.getReferenceById(applicationId);
        application.setSesCode(createSesCode());

        applicationRepository.save(application);
        EmailMessage emailMessage = createEmailMessage(application, Theme.SEND_SES);
        dossierService.sendMessage(emailMessage);
    }


    public void code(UUID applicationId, Integer sesCode) {
        Application application = applicationRepository.getReferenceById(applicationId);

        if (!Objects.equals(application.getSesCode(), sesCode)) {
            throw new DealException("Incorrect code");
        }
        application.setSignDate(Timestamp.from(Instant.now()));

        dealService.updateStatus(application, ApplicationStatus.DOCUMENT_SIGNED, MANUAL);

        applicationRepository.save(application);

        issueCredit(applicationId);
    }

    public void issueCredit(UUID applicationId) {
        Application application = applicationRepository.getReferenceById(applicationId);
        UUID id = application.getCredit().getId();
        Credit credit = creditRepository.getReferenceById(id);

        dealService.updateStatus(application, CREDIT_ISSUED, AUTOMATIC);
        credit.setStatus(CreditStatus.ISSUED);
        creditRepository.save(credit);

        EmailMessage emailMessage = createEmailMessage(application, Theme.CREDIT_ISSUED);
        dossierService.sendMessage(emailMessage);
    }

    private static EmailMessage createEmailMessage(Application application, Theme theme) {
        return EmailMessage.builder()
                .applicationId(application.getId())
                .theme(theme)
                .address(application.getClient().getEmail())
                .build();
    }
}
