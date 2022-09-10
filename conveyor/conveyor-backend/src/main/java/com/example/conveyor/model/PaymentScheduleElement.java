package com.example.conveyor.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PaymentScheduleElement {
    /** Номер платежа */
    private Integer number;
    /** Дата предстоящего платежа */
    private LocalDate date;
    /** Общая сумма оплаты */
    private BigDecimal totalPayment;
    /** Погашение основного долга */
    private BigDecimal debtPayment;
    /** Процент платежа */
    private BigDecimal interestPayment;
    /** Остаток основного долга */
    private BigDecimal remainingDebt;
}