package com.example.conveyor.service;

import com.example.conveyor.config.AppConfig;
import com.example.conveyor.exception.ScoringException;
import com.example.conveyor.model.CreditInfo;
import com.example.conveyor.model.PaymentScheduleElement;
import com.example.conveyor.model.ScoringInfo;
import com.example.conveyor.service.util.CalcUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.example.conveyor.model.Employment.EmploymentStatus.*;
import static com.example.conveyor.model.ScoringInfo.Marital.MARRIED;
import static com.example.conveyor.model.ScoringInfo.Marital.REMARRIED;

@Service
@RequiredArgsConstructor
public class ScoringService {

    private final AppConfig appConfig;

    public CreditInfo createCredit(ScoringInfo scoringInfo) {
        BigDecimal rate = calculateRate(appConfig.getBaseRate(), scoringInfo);
        Integer term = scoringInfo.getTerm();
        BigDecimal amount = scoringInfo.getAmount();
        BigDecimal monthlyPayment = CalcUtil.getMonthlyPayment(rate, amount, term);
        List<PaymentScheduleElement> payments = getPaymentScheduleElements(rate, term, monthlyPayment, amount);
        BigDecimal totalAmount = CalcUtil.getTotalAmount(payments);

        CreditInfo creditInfo = new CreditInfo();
        creditInfo.setAmount(amount);
        creditInfo.setTerm(term);
        creditInfo.setRate(rate);
        creditInfo.setSalaryClient(scoringInfo.isSalaryClient());
        creditInfo.setInsuranceEnabled(scoringInfo.isInsuranceEnabled());
        creditInfo.setMonthlyPayment(monthlyPayment);
        creditInfo.setPaymentSchedule(payments);
        creditInfo.setPsk(totalAmount);
        return creditInfo;
    }


    private BigDecimal calculateRate(BigDecimal rate, ScoringInfo scoringInfo) {
        BigDecimal currentRate = rate;
        if (scoringInfo.getAmount().compareTo(
                scoringInfo.getEmployment().getSalary().multiply(BigDecimal.valueOf(20))) > 0) {
            throw new ScoringException("Loan amount is more than 20 salaries → denial");
        }
        if (scoringInfo.getEmployment().getWorkExperienceTotal() < 12) {
            throw new ScoringException("Total experience of less than 12 months → denial");
        }
        if (scoringInfo.getEmployment().getWorkExperienceCurrent() < 3) {
            throw new ScoringException("Current experience less than 3 months → denial");
        }
        if (scoringInfo.getEmployment().getEmploymentStatus() == UNEMPLOYED) {
            throw new ScoringException("Unemployed status → denial");
        }

        if (scoringInfo.isInsuranceEnabled()) {
            currentRate = new BigDecimal(String.valueOf(currentRate.subtract(BigDecimal.ONE)));
        }
        if (scoringInfo.isSalaryClient()) {
            currentRate = new BigDecimal(String.valueOf(currentRate.subtract(BigDecimal.valueOf(2))));
        }
        if (scoringInfo.getEmployment().getEmploymentStatus() == SELF_EMPLOYED) {
            currentRate = new BigDecimal(String.valueOf(currentRate.add(BigDecimal.valueOf(1))));
        } else if (scoringInfo.getEmployment().getEmploymentStatus() == BUSINESS_OWNER) {
            currentRate = new BigDecimal(String.valueOf(currentRate.add(BigDecimal.valueOf(3))));
        }

        if (scoringInfo.getMaritalStatus() == MARRIED) {
            currentRate = new BigDecimal(String.valueOf(currentRate.subtract(BigDecimal.valueOf(3))));
        } else if (scoringInfo.getMaritalStatus() == REMARRIED) {
            currentRate = new BigDecimal(String.valueOf(currentRate.add(BigDecimal.valueOf(1))));
        }
        if (scoringInfo.getDependentAmount() > 1) {
            currentRate = new BigDecimal(String.valueOf(currentRate.add(BigDecimal.valueOf(1))));
        }
        if (Period.between(scoringInfo.getBirthdate(), LocalDate.now()).getYears() > 60) {
            currentRate = new BigDecimal(String.valueOf(currentRate.add(BigDecimal.valueOf(1))));
        }
        return currentRate;
    }

    /** Получить график платежей */
    private List<PaymentScheduleElement> getPaymentScheduleElements(BigDecimal rate, Integer term,
                                                                   BigDecimal monthlyPayment, BigDecimal amount) {
        List<PaymentScheduleElement> paymentScheduleElementList = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        BigDecimal remainingDebt = amount;

        for (int i = 0; i < term; i++) {
            LocalDate startDate = currentDate.plusMonths(i);
            LocalDate endDate = startDate.plusMonths(1);
            long between = ChronoUnit.DAYS.between(startDate, endDate);
            int lengthOfYear = startDate.lengthOfYear();

            BigDecimal interestPayment = CalcUtil.getInterestPayment(rate, remainingDebt, between, lengthOfYear);
            BigDecimal debtPayment = getDebtPayment(monthlyPayment, interestPayment, remainingDebt);
            remainingDebt = remainingDebt.subtract(debtPayment);
            BigDecimal totalPayment = debtPayment.add(interestPayment);

            PaymentScheduleElement paymentScheduleElement = new PaymentScheduleElement();
            paymentScheduleElement.setNumber(i + 1);
            paymentScheduleElement.setDate(startDate);
            paymentScheduleElement.setTotalPayment(totalPayment);
            paymentScheduleElement.setInterestPayment(interestPayment);
            paymentScheduleElement.setDebtPayment(debtPayment);
            paymentScheduleElement.setRemainingDebt(remainingDebt);
            paymentScheduleElementList.add(paymentScheduleElement);
        }

        return paymentScheduleElementList;
    }

    /** Получить основной долг */
    private static BigDecimal getDebtPayment(BigDecimal monthlyPayment, BigDecimal interestPayment,
                                             BigDecimal remainingDebt) {
        // основной долг = ежемесячный платеж - сумма процентов
        // Если основной долг больше чем остаток выплат, то погашение основного долга = остатку выплат
        BigDecimal debtPayment = monthlyPayment.subtract(interestPayment);
        if (debtPayment.compareTo(remainingDebt) > 0) {
            return remainingDebt;
        }

        return debtPayment;
    }
}
