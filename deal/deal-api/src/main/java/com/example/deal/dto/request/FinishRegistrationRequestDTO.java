package com.example.deal.dto.request;

import com.example.deal.dto.GenderDTO;
import com.example.deal.dto.MaritalDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class FinishRegistrationRequestDTO {
    @Schema(description = "Количество иждивенцев", example = "1", required = true)
    private Integer dependentAmount;
    @NotNull
    @Schema(description = "Пол", required = true)
    private GenderDTO gender;
    @Schema(description = "Дата выдачи паспорта", example = "2006-06-21")
    private LocalDate passportIssueDate;
    @Schema(description = "Место выдачи паспорта", example = "ТП №43 отдела УФМС России по Санкт-Петербургу и Ленинградской области")
    private String passportIssueBranch;
    private EmploymentDTO employment;
    @Schema(description = "Семейное положение", required = true)
    private MaritalDTO maritalStatus;
    @Schema(description = "Счет клиента")
    private String account;
}