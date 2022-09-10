package com.example.conveyor.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CreditInfo {
    /** Сумма кредита */
    private BigDecimal amount;
    /** Срок кредита */
    private Integer term;
    /** Ставка кредита */
    private BigDecimal rate;
    /** Зарплатный клиент */
    private boolean isSalaryClient;
    /** Наличие страховки */
    private boolean isInsuranceEnabled;
    /** Ежемесячный платеж */
    private BigDecimal monthlyPayment;
    /** График платежей */
    private List<PaymentScheduleElement> paymentSchedule;
    /** Полная стоимость кредита */
    private BigDecimal psk;
}
