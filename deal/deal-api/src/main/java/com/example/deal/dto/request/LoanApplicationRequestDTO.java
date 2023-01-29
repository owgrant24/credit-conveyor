package com.example.deal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanApplicationRequestDTO {
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
    @NotNull
    @Schema(description = "День Рождения", example = "1992-06-21", required = true)
    private LocalDate birthdate;
    @NotNull
    @Email
    @Schema(description = "Электронный адрес", example = "email@ya.ru", required = true)
    private String email;
    @NotNull
    @Min(10_000)
    @Digits(integer = 15, fraction = 2)
    @Schema(description = "Сумма кредита", example = "300000.00", required = true)
    private BigDecimal amount;
    @NotNull
    @Min(value = 6)
    @Schema(description = "Срок кредита", example = "18", required = true)
    private Integer term;
}