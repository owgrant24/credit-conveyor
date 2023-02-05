package com.example.application.builder;

import com.example.application.dto.LoanOfferDTO;

import java.math.BigDecimal;
import java.util.UUID;

public final class LoanOfferDTOBuilder {
    private UUID applicationId;
    private BigDecimal totalAmount;
    private Boolean isSalaryClient;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal requestedAmount;
    private Integer term;
    private Boolean isInsuranceEnabled;

    private LoanOfferDTOBuilder() {
    }

    public static LoanOfferDTOBuilder builder() {
        return new LoanOfferDTOBuilder();
    }

    public LoanOfferDTOBuilder withApplicationId(UUID applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public LoanOfferDTOBuilder withTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public LoanOfferDTOBuilder withIsSalaryClient(Boolean isSalaryClient) {
        this.isSalaryClient = isSalaryClient;
        return this;
    }

    public LoanOfferDTOBuilder withMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
        return this;
    }

    public LoanOfferDTOBuilder withRate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    public LoanOfferDTOBuilder withRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
        return this;
    }

    public LoanOfferDTOBuilder withTerm(Integer term) {
        this.term = term;
        return this;
    }

    public LoanOfferDTOBuilder withIsInsuranceEnabled(Boolean isInsuranceEnabled) {
        this.isInsuranceEnabled = isInsuranceEnabled;
        return this;
    }

    public LoanOfferDTO build() {
        return new LoanOfferDTO(applicationId, totalAmount, isSalaryClient, monthlyPayment, rate, requestedAmount, term,
                isInsuranceEnabled);
    }
}
