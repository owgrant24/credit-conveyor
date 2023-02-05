package com.example.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanApplicationRequestDTO {
    @Schema(description = "Имя", example = "Сергей", required = true)
    private String firstName;
    @NotNull
    @Schema(description = "Фамилия", example = "Ершов", required = true)
    private String lastName;
    @Schema(description = "Отчество", example = "Иванович")
    private String middleName;
    @NotNull
    @Schema(description = "Номер паспорта", example = "564333", required = true)
    private String passportNumber;
    @NotNull
    @Schema(description = "Серия паспорта", example = "4011", required = true)
    private String passportSeries;
    @NotNull
    @Schema(description = "День Рождения", example = "1992-06-21", required = true)
    private LocalDate birthdate;
    @NotNull
    @Schema(description = "Электронный адрес", example = "email@ya.ru", required = true)
    private String email;
    @NotNull
    @Schema(description = "Сумма кредита", example = "300000.00", required = true)
    private BigDecimal amount;
    @NotNull
    @Schema(description = "Срок кредита", example = "18", required = true)
    private Integer term;
}