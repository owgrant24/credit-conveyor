package com.example.deal.service;

import com.example.deal.db.entity.Application;
import com.example.deal.db.entity.Credit;
import com.example.deal.db.enums.ApplicationStatus;
import com.example.deal.db.enums.CreditStatus;
import com.example.deal.db.helper.ApplicationHelper;
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
    private final ApplicationHelper applicationHelper;
    private final DossierService dossierService;
    private final CreditRepository creditRepository;

    public void createDocuments(Application application) {
        EmailMessage emailMessage = createEmailMessage(application, Theme.CREATE_DOCUMENT);
        dossierService.sendMessage(emailMessage);
    }

    public void send(UUID applicationId) {
        Application application = applicationHelper.getById(applicationId);
        EmailMessage emailMessage = createEmailMessage(application, Theme.SEND_DOCUMENT);
        dossierService.sendMessage(emailMessage);
    }

    public void sign(UUID applicationId) {
        Application application = applicationHelper.getById(applicationId);
        application.setSesCode(createSesCode());

        applicationHelper.save(application);
        EmailMessage emailMessage = createEmailMessage(application, Theme.SEND_SES,
                String.valueOf(application.getSesCode()));
        dossierService.sendMessage(emailMessage);
    }


    public void code(UUID applicationId, Integer sesCode) {
        Application application = applicationHelper.getById(applicationId);

        if (!Objects.equals(application.getSesCode(), sesCode)) {
            throw new DealException("Incorrect code");
        }
        application.setSignDate(Timestamp.from(Instant.now()));

        applicationHelper.saveAndUpdateStatus(application, ApplicationStatus.DOCUMENT_SIGNED, MANUAL);

        issueCredit(applicationId);
    }

    public void issueCredit(UUID applicationId) {
        Application application = applicationHelper.getById(applicationId);
        UUID id = application.getCredit().getId();
        Credit credit = creditRepository.getReferenceById(id);

        applicationHelper.saveAndUpdateStatus(application, CREDIT_ISSUED, AUTOMATIC);
        credit.setStatus(CreditStatus.ISSUED);
        creditRepository.save(credit);

        EmailMessage emailMessage = createEmailMessage(application, Theme.CREDIT_ISSUED);
        dossierService.sendMessage(emailMessage);
    }

    private static EmailMessage createEmailMessage(Application application, Theme theme) {
        return createEmailMessage(application, theme, null);
    }

    private static EmailMessage createEmailMessage(Application application, Theme theme, String text) {
        return EmailMessage.builder()
                .applicationId(application.getId())
                .theme(theme)
                .address(application.getClient().getEmail())
                .text(text)
                .build();
    }
}
