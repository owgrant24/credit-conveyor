package com.example.deal.integration.dossier;

import com.example.deal.dto.EmailMessage;
import com.example.deal.dto.Theme;
import com.example.deal.integration.dossier.config.DossierConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DossierService {

    private final DossierConfig dossierConfig;
    private final KafkaTemplate<String, EmailMessage> dossierKafkaTemplate;

    public void sendMessage(EmailMessage emailMessage) {
        String topic = selectTopic(emailMessage.getTheme());
        String applicationId = emailMessage.getApplicationId().toString();
        dossierKafkaTemplate.send(topic, applicationId, emailMessage);
    }

    private String selectTopic(Theme theme) {
        var topic = dossierConfig.getTopic();
        return switch (theme) {
            case FINISH_REGISTRATION -> topic.getFinishRegistration();
            case CREATE_DOCUMENT -> topic.getCreateDocument();
            case SEND_DOCUMENT -> topic.getSendDocument();
            case SEND_SES -> topic.getSendSes();
            case CREDIT_ISSUED -> topic.getCreditIssued();
            case APPLICATION_DENIED -> topic.getApplicationDenied();
        };
    }
}
