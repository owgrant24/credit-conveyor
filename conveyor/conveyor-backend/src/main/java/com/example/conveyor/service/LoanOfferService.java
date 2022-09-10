package com.example.conveyor.service;

import com.example.conveyor.config.AppConfig;
import com.example.conveyor.model.LoanInfo;
import com.example.conveyor.model.LoanOffer;
import com.example.conveyor.service.util.CalcUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanOfferService {

    private final AppConfig appConfig;

    public List<LoanOffer> createOffers(LoanInfo loanInfo) {
        BigDecimal baseRate = appConfig.getBaseRate();

        LoanOffer offer1 = calculate(loanInfo, baseRate, true, true);
        LoanOffer offer2 = calculate(loanInfo, baseRate, false, true);
        LoanOffer offer3 = calculate(loanInfo, baseRate, true, false);
        LoanOffer offer4 = calculate(loanInfo, baseRate, false, false);
        return List.of(offer1, offer2, offer3, offer4);
    }

    private LoanOffer calculate(LoanInfo loanInfo, BigDecimal baseRate, boolean insurance, boolean salaryClient) {
        BigDecimal rate = calculateRate(baseRate, insurance, salaryClient);
        BigDecimal monthlyPayment = CalcUtil.getMonthlyPayment(rate, loanInfo.getAmount(), loanInfo.getTerm());
        BigDecimal totalAmount = monthlyPayment.multiply(new BigDecimal(loanInfo.getTerm()));

        LoanOffer loanOffer = new LoanOffer();
        loanOffer.setApplicationId(UUID.randomUUID());
        loanOffer.setSalaryClient(salaryClient);
        loanOffer.setRate(rate);
        loanOffer.setTerm(loanInfo.getTerm());
        loanOffer.setRequestedAmount(loanInfo.getAmount());
        loanOffer.setMonthlyPayment(monthlyPayment);
        loanOffer.setTotalAmount(totalAmount);
        loanOffer.setInsuranceEnabled(insurance);

        return loanOffer;
    }

    private BigDecimal calculateRate(BigDecimal rate, boolean insurance, boolean salaryClient) {
        BigDecimal currentRate = rate;
        if (insurance) {
            currentRate = new BigDecimal(String.valueOf(currentRate.subtract(BigDecimal.ONE)));
        }
        if (salaryClient) {
            currentRate = new BigDecimal(String.valueOf(currentRate.subtract(BigDecimal.valueOf(2))));
        }
        return currentRate;
    }
}
