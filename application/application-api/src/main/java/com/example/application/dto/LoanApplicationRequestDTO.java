package com.example.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanApplicationRequestDTO(
        @NotNull
        @Schema(description = "Имя", example = "Сергей", required = true)
        String firstName,
        @NotNull
        @Schema(description = "Фамилия", example = "Ершов", required = true)
        String lastName,
        @Schema(description = "Отчество", example = "Иванович")
        String middleName,
        @NotNull
        @Schema(description = "Номер паспорта", example = "564333", required = true)
        String passportNumber,
        @NotNull
        @Schema(description = "Серия паспорта", example = "4011", required = true)
        String passportSeries,
        @NotNull
        @Schema(description = "День Рождения", example = "1992-06-21", required = true)
        LocalDate birthdate,
        @NotNull
        @Schema(description = "Электронный адрес", example = "email@ya.ru", required = true)
        String email,
        @NotNull
        @Schema(description = "Сумма кредита", example = "300000.00", required = true)
        BigDecimal amount,
        @NotNull
        @Schema(description = "Срок кредита", example = "18", required = true)
        Integer term
) {

}