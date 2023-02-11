package com.example.conveyor.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Schema(description = "День Рождения", example = "1992-06-21", required = true)
    private LocalDate birthdate;
    @Email
    @Schema(description = "Электронный адрес", example = "email@ya.ru")
    private String email;
    @NotNull
    @Min(10_000)
    @Digits(integer = 15, fraction = 2)
    @Schema(description = "Сумма кредита", example = "11000.00", required = true)
    private BigDecimal amount;
    @NotNull
    @Min(value = 6)
    @Schema(description = "Срок кредита", example = "7", required = true)
    private Integer term;
}