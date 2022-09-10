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
public class Employment {
    /** Текущий стаж работы */
    private Integer workExperienceCurrent;
    /** Общий стаж работы */
    private Integer workExperienceTotal;
    /** Зарплата */
    private BigDecimal salary;
    private Position position;
    private EmploymentStatus employmentStatus;

    public enum EmploymentStatus {
        /** Безработный */
        UNEMPLOYED,
        /** Самозанятый */
        SELF_EMPLOYED,
        /** Работник */
        EMPLOYED,
        /** Владелец бизнеса */
        BUSINESS_OWNER
    }

    public enum Position {
        WORKER,
        /** Менеджер среднего звена */
        MID_MANAGER,
        /** Топ-менеджер */
        TOP_MANAGER,
        OWNER
    }
}
