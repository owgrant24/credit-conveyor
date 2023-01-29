package com.example.deal.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentScheduleElementDTO {
    @Schema(description = "Номер платежа", example = "1", required = true)
    private Integer number;
    @Schema(description = "Дата предстоящего платежа", example = "11000.00", required = true)
    private LocalDate date;
    @Schema(description = "Общая сумма оплаты", example = "11000.00", required = true)
    private BigDecimal totalPayment;
    @Schema(description = "Процент платежа", example = "11000.00", required = true)
    private BigDecimal interestPayment;
    @Schema(description = "Погашение основного долга", example = "11000.00", required = true)
    private BigDecimal debtPayment;
    @Schema(description = "Остаток основного долга", example = "200000.00", required = true)
    private BigDecimal remainingDebt;
}