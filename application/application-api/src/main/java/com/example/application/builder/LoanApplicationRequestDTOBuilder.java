package com.example.application.builder;

import com.example.application.dto.LoanApplicationRequestDTO;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public final class LoanApplicationRequestDTOBuilder {
    private @NotNull String firstName;
    private @NotNull String lastName;
    private String middleName;
    private @NotNull String passportNumber;
    private @NotNull String passportSeries;
    private @NotNull LocalDate birthdate;
    private @NotNull String email;
    private @NotNull BigDecimal amount;
    private @NotNull Integer term;

    private LoanApplicationRequestDTOBuilder() {
    }

    public static LoanApplicationRequestDTOBuilder builder() {
        return new LoanApplicationRequestDTOBuilder();
    }

    public LoanApplicationRequestDTOBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public LoanApplicationRequestDTOBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public LoanApplicationRequestDTOBuilder withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public LoanApplicationRequestDTOBuilder withPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    public LoanApplicationRequestDTOBuilder withPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
        return this;
    }

    public LoanApplicationRequestDTOBuilder withBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public LoanApplicationRequestDTOBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public LoanApplicationRequestDTOBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public LoanApplicationRequestDTOBuilder withTerm(Integer term) {
        this.term = term;
        return this;
    }

    public LoanApplicationRequestDTO build() {
        return new LoanApplicationRequestDTO(
                firstName, lastName, middleName, passportNumber, passportSeries, birthdate, email, amount, term);
    }
}
