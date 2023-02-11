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
public class ScoringInfo {
    /** Имя */
    private String firstName;
    /** Фамилия */
    private String lastName;
    /** Отчество */
    private String middleName;
    /** Номер паспорта */
    private String passportNumber;
    /** Серия паспорта */
    private String passportSeries;
    /** Дата выдачи паспорта */
    private LocalDate passportIssueDate;
    /** Место выдачи паспорта */
    private String passportIssueBranch;
    /** День Рождения */
    private LocalDate birthdate;
    /** Сумма кредита */
    private BigDecimal amount;
    /** Срок кредита */
    private Integer term;
    /** Количество иждивенцев */
    private Integer dependentAmount;
    /** Зарплатный клиент */
    private boolean isSalaryClient;
    /** Застрахованный клиент */
    private boolean isInsuranceEnabled;
    /** Пол */
    private Gender gender;
    /** Семейное положение */
    private Employment employment;
    private Marital maritalStatus;
    /** Счет клиента */
    private String account;

    public enum Gender {
        NON_BINARY,
        MALE,
        FEMALE
    }

    public enum Marital {
        SINGLE,
        DIVORCED,
        WIDOW_WIDOWER,
        MARRIED,
        REMARRIED
    }
}
