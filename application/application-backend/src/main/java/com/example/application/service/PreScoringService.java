package com.example.application.service;

import com.example.application.dto.LoanApplicationRequestDTO;
import com.example.application.exception.PreScoringException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PreScoringService {

    private static final String REGEX_NAME = "[A-Za-zА-Яа-яЁё\\-]{2,30}";
    private static final String MIN_AMOUNT = "10000";
    private static final int MIN_AGE = 18;
    private static final int MIN_TERM = 6;
    private static final String REGEX_PASSPORT_NUMBER = "\\d{6}";
    private static final String REGEX_PASSPORT_SERIES = "\\d{4}";

    public void validate(LoanApplicationRequestDTO request) {
        List<String> causes = new ArrayList<>();
        if (!request.firstName().matches(REGEX_NAME)) {
            causes.add("FirstName - incorrect format");
        }
        if (!request.lastName().matches(REGEX_NAME)) {
            causes.add("LastName - incorrect format");
        }
        if (Objects.nonNull(request.middleName()) && !request.middleName().matches(REGEX_NAME)) {
            causes.add("MiddleName - incorrect format");
        }
        if (!request.passportNumber().matches(REGEX_PASSPORT_NUMBER)) {
            causes.add("Passport number - incorrect format");
        }
        if (!request.passportSeries().matches(REGEX_PASSPORT_SERIES)) {
            causes.add("Passport series - incorrect format");
        }
        if (ChronoUnit.YEARS.between(request.birthdate(), LocalDate.now()) < MIN_AGE) {
            causes.add("Age < " + MIN_AGE);
        }
        if (!EmailValidator.getInstance().isValid(request.email())) {
            causes.add("Email - incorrect format");
        }
        if (request.amount().compareTo(new BigDecimal(MIN_AMOUNT)) < 0) {
            causes.add("Credit amount < " + MIN_AMOUNT);
        }
        if (request.term() < MIN_TERM) {
            causes.add("Term < " + MIN_TERM);
        }

        if (!causes.isEmpty()) {
            String message = "\n" + String.join(" \n", causes);
            throw new PreScoringException(message);
        }
    }
}
