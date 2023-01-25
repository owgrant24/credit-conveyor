package com.example.conveyor.service;

import com.example.conveyor.config.AppConfig;
import com.example.conveyor.model.LoanInfo;
import com.example.conveyor.model.LoanOffer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanOfferServiceTest {

    private static final BigDecimal BASE_RATE = new BigDecimal("15");

    @InjectMocks
    private LoanOfferService loanOfferService;
    @Mock
    private AppConfig appConfig;

    @Test
    void createOffers() {
        when(appConfig.getBaseRate()).thenReturn(BASE_RATE);
        LoanInfo loanInfo = new LoanInfo();
        loanInfo.setAmount(new BigDecimal("300000"));
        loanInfo.setTerm(18);

        List<LoanOffer> loanOffers = loanOfferService.createOffers(loanInfo);

        assertAll(
                () -> assertEquals(4, loanOffers.size()),
                () -> assertEquals(new BigDecimal(12), loanOffers.get(0).getRate()),
                () -> assertEquals(new BigDecimal("18294.61"), loanOffers.get(0).getMonthlyPayment()),
                () -> assertEquals(new BigDecimal("329302.98"), loanOffers.get(0).getTotalAmount()),
                () -> assertEquals(new BigDecimal(13), loanOffers.get(1).getRate()),
                () -> assertEquals(new BigDecimal("18434.28"), loanOffers.get(1).getMonthlyPayment()),
                () -> assertEquals(new BigDecimal("331817.04"), loanOffers.get(1).getTotalAmount()),
                () -> assertEquals(new BigDecimal(14), loanOffers.get(2).getRate()),
                () -> assertEquals(new BigDecimal("18574.55"), loanOffers.get(2).getMonthlyPayment()),
                () -> assertEquals(new BigDecimal("334341.90"), loanOffers.get(2).getTotalAmount()),
                () -> assertEquals(BASE_RATE, loanOffers.get(3).getRate()),
                () -> assertEquals(new BigDecimal("18715.44"), loanOffers.get(3).getMonthlyPayment()),
                () -> assertEquals(new BigDecimal("336877.92"), loanOffers.get(3).getTotalAmount())
        );
    }
}