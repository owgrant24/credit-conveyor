package com.example.conveyor.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LoanInfo {
    /** Сумма кредита */
    private BigDecimal amount;
    /** Срок кредита */
    private Integer term;
}
