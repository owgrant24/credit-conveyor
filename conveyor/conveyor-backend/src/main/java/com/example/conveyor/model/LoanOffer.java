package com.example.conveyor.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LoanOffer {
    /** Идентификатор предложения */
    private UUID applicationId;
    /** Полная стоимость кредита */
    private BigDecimal totalAmount;
    /** Зарплатный клиент */
    private boolean isSalaryClient;
    /** Ежемесячный платеж */
    private BigDecimal monthlyPayment;
    /** Ставка кредита */
    private BigDecimal rate;
    /** Сумма кредита */
    private BigDecimal requestedAmount;
    /** Срок кредита */
    private Integer term;
    /** Застрахованный клиент */
    private boolean isInsuranceEnabled;
}
