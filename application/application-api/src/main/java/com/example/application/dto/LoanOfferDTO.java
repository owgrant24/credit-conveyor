package com.example.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;


public record LoanOfferDTO(
        @Schema(description = "Идентификатор", example = "ec1fe120-3f2d-4676-b39e-40556cad0473", required = true)
        UUID applicationId,
        @Schema(description = "Полная стоимость кредита", example = "111000.00", required = true)
        BigDecimal totalAmount,
        @Schema(description = "Зарплатный клиент", required = true)
        Boolean isSalaryClient,
        @Schema(description = "Ежемесячный платеж", example = "11000.00", required = true)
        BigDecimal monthlyPayment,
        @Schema(description = "Ставка кредита", example = "15", required = true)
        BigDecimal rate,
        @Schema(description = "Запрашиваемая сумма кредита", example = "100000.00", required = true)
        BigDecimal requestedAmount,
        @Schema(description = "Срок кредита", example = "18", required = true)
        Integer term,
        @Schema(description = "Наличие страховки", required = true)
        Boolean isInsuranceEnabled
) {

}