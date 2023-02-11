package com.example.conveyor.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ScoringDataDTO {
    @NotNull
    @Size(min = 2, max = 30)
    @Schema(description = "Имя", example = "Сергей", required = true)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 30)
    @Schema(description = "Фамилия", example = "Ершов", required = true)
    private String lastName;
    @Size(min = 2, max = 30)
    @Schema(description = "Отчество", example = "Иванович")
    private String middleName;
    @NotNull
    @Size(min = 6, max = 6)
    @Schema(description = "Номер паспорта", example = "564333", required = true)
    private String passportNumber;
    @NotNull
    @Size(min = 4, max = 4)
    @Schema(description = "Серия паспорта", example = "4011", required = true)
    private String passportSeries;
    @Schema(description = "Дата выдачи паспорта", example = "2006-06-21")
    private LocalDate passportIssueDate;
    @Schema(description = "Место выдачи паспорта", example = "ТП №43 отдела УФМС России по Санкт-Петербургу и Ленинградской области")
    private String passportIssueBranch;
    @NotNull
    @Schema(description = "День Рождения", example = "1992-06-21", required = true)
    private LocalDate birthdate;
    @NotNull
    @Min(10_000)
    @Digits(integer = 15, fraction = 2)
    @Schema(description = "Сумма кредита", example = "300000.00", required = true)
    private BigDecimal amount;
    @NotNull
    @Min(value = 6)
    @Schema(description = "Срок кредита", example = "18", required = true)
    private Integer term;
    @NotNull
    @Schema(description = "Количество иждивенцев", example = "1", required = true)
    private Integer dependentAmount;
    @NotNull
    @Schema(description = "Зарплатный клиент", required = true)
    private Boolean isSalaryClient;
    @NotNull
    @Schema(description = "Застрахованный клиент", required = true)
    private Boolean isInsuranceEnabled;
    @NotNull
    @Schema(description = "Пол", required = true)
    private GenderDTO gender;
    @Valid
    @NotNull
    private EmploymentDTO employment;
    @NotNull
    @Schema(description = "Семейное положение", required = true)
    private MaritalDTO maritalStatus;
    @Schema(description = "Счет клиента")
    private String account;

    public enum GenderDTO {
        MALE,
        FEMALE,
        NON_BINARY
    }

    public enum MaritalDTO {
        SINGLE,
        DIVORCED,
        WIDOW_WIDOWER,
        MARRIED,
        REMARRIED
    }
}