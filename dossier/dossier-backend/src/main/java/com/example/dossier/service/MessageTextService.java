package com.example.dossier.service;

import com.example.dossier.kafka.model.Message;
import com.example.dossier.model.EmailMessage;
import org.springframework.stereotype.Service;

@Service
public class MessageTextService {

    public EmailMessage createEmailMessage(Message message) {
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setAddress(message.getAddress());

        switch (message.getTheme()) {
            case CREATE_DOCUMENT -> {
                emailMessage.setSubject("Создание документа");
                emailMessage.setText(
                        "Скоринг прошёл успешно, для продолжения надо отправить запрос на создание документов");
            }
            case FINISH_REGISTRATION -> {
                emailMessage.setSubject("Завершение регистрации");
                emailMessage.setText("Необходимо продолжить регистрацию, для оформления кредита");
            }
            case SEND_DOCUMENT -> {
                emailMessage.setSubject("Необходимые документы");
                emailMessage.setText(
                        "Просмотрите документы в этом письме, и отправьте запрос на подписание документов");
            }
            case SEND_SES -> {
                emailMessage.setSubject("Специальный код");
                emailMessage.setText(
                        "Код: " + message.getText() + " необходимо прислать вместе с id заявки на подписание");
            }
            case CREDIT_ISSUED -> {
                emailMessage.setSubject("Подтверждение успешного взятия кредита");
                emailMessage.setText("Кредит успешно выдан!");
            }
            case APPLICATION_DENIED -> {
                emailMessage.setSubject("Отмена взятия кредита");
                emailMessage.setText("Заявка отклонена");
            }
        }
        return emailMessage;
    }
}
