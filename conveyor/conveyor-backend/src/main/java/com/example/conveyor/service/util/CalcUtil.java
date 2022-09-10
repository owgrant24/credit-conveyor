package com.example.conveyor.service.util;

import com.example.conveyor.model.PaymentScheduleElement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalcUtil {
    private static final String MONTHS_IN_YEAR = "12";

    /**
     * Расчет ежемесячной оплаты (аннуитетный платеж)
     * Аннуитетный платеж = Сумма кредита * коэффициент аннуитета
     */
    public static BigDecimal getMonthlyPayment(BigDecimal rate, BigDecimal amount, Integer term) {
        BigDecimal annuityPayment = getAnnuityPayment(rate, term);
        BigDecimal monthlyPayment = amount.multiply(annuityPayment);
        return normalize(monthlyPayment);
    }

    /**
     * Расчет суммы процентов
     * Остаток долга × Процентная ставка × Количество дней в месяце / Количество дней в году
     */
    public static BigDecimal getInterestPayment(BigDecimal rate, BigDecimal remainingDebt, long between,
                                                int lengthOfYear) {
        BigDecimal decimalRate = convertToDecimal(rate);
        BigDecimal part1 = remainingDebt.multiply(decimalRate);
        BigDecimal part2 = part1.multiply(BigDecimal.valueOf(between));
        return part2.divide(BigDecimal.valueOf(lengthOfYear), 2, RoundingMode.HALF_UP);
    }

    /**
     * Расчёт полный суммы кредита
     * Полная сумма кредита = ∑ всех платежей
     */
    public static BigDecimal getTotalAmount(List<PaymentScheduleElement> payments) {
        return payments.stream()
                .map(PaymentScheduleElement::getTotalPayment)
                .reduce((BigDecimal::add))
                .orElseThrow();
    }

    /**
     * Расчет аннуитетного платежа
     * (МП * (1 + МП) ^ КП) / ((1 + МП) ^ КП - 1)
     * МП - Месячная процентная ставка
     * КП - Количество платежей
     */
    private static BigDecimal getAnnuityPayment(BigDecimal rate, Integer term) {
        BigDecimal decimalMonthlyPaymentRate = convertToDecimal(getMonthlyPaymentRate(rate));
        BigDecimal part = BigDecimal.ONE.add(decimalMonthlyPaymentRate).pow(term);              // (1 + МП) ^ КП
        BigDecimal divisible = decimalMonthlyPaymentRate.multiply(part);                        // (МП * (1 + МП) ^ КП)
        BigDecimal divider = part.subtract(BigDecimal.ONE);                                     // ((1 + МП) ^ КП - 1)
        return divisible.divide(divider, 10, RoundingMode.HALF_UP);
    }

    /**
     * Расчет месячной процентной ставки:
     * Месячная процентная ставка = Процентная ставка (в годовых) / 12 месяцев
     * 15% / 12 = 1,25%
     */
    private static BigDecimal getMonthlyPaymentRate(BigDecimal rate) {
        return rate.divide(new BigDecimal(MONTHS_IN_YEAR), 10, RoundingMode.HALF_UP);
    }

    /**
     * Перевод процентов в десятичную дробь
     * 1,25 / 100 = 0,0125
     */
    private static BigDecimal convertToDecimal(BigDecimal interest) {
        return interest.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
    }

    /**
     * Округление денежных средств
     * 10,2567 -> 10,26
     */
    private static BigDecimal normalize(BigDecimal number) {
        return number.setScale(2, RoundingMode.HALF_UP);
    }
}
