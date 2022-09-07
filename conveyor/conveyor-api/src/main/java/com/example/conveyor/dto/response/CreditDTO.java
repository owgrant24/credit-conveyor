package com.example.conveyor.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CreditDTO {
    @Min(10_000)
    @Digits(integer = 15, fraction = 2)
    @Schema(description = "Сумма кредита", example = "300000.00", required = true)
    private BigDecimal amount;
    @Min(value = 6)
    @Schema(description = "Срок кредита", example = "18", required = true)
    private Integer term;
    @Schema(description = "Ставка кредита", example = "15", required = true)
    private BigDecimal rate;
    @Schema(description = "Зарплатный клиент", required = true)
    private Boolean isSalaryClient;
    @Schema(description = "Наличие страховки", required = true)
    private Boolean isInsuranceEnabled;
    @Schema(description = "Ежемесячный платеж", example = "33000", required = true)
    private BigDecimal monthlyPayment;
    @Schema(description = "График платежей", required = true)
    private List<PaymentScheduleElementDTO> paymentSchedule;
    @Schema(description = "Полная стоимость кредита", required = true)
    private BigDecimal psk;
}