package com.example.conveyor.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class EmploymentDTO {
    @NotNull
    @Schema(description = "Текущий стаж работы", example = "15", required = true)
    private Integer workExperienceCurrent;
    @NotNull
    private PositionDTO position;
    @NotNull
    @Schema(description = "Общий стаж работы", example = "15", required = true)
    private Integer workExperienceTotal;
    @NotNull
    private EmploymentStatusDTO employmentStatus;
    @NotNull
    @Schema(description = "Зарплата", example = "90000", required = true)
    private BigDecimal salary;
    @Schema(description = "ИНН", example = "132123123123")
    private String employerINN;

    public enum EmploymentStatusDTO {
        /** Работник */
        EMPLOYED,
        /** Безработный */
        UNEMPLOYED,
        /** Самозанятый */
        SELF_EMPLOYED,
        /** Владелец бизнеса */
        BUSINESS_OWNER
    }

    public enum PositionDTO {
        WORKER,
        /** Менеджер среднего звена*/
        MID_MANAGER,
        /** Топ-менеджер */
        TOP_MANAGER,
        OWNER
    }
}