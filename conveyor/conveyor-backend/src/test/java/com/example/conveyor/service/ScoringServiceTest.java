package com.example.conveyor.service;

import com.example.conveyor.config.AppConfig;
import com.example.conveyor.exception.ScoringException;
import com.example.conveyor.model.CreditInfo;
import com.example.conveyor.model.Employment;
import com.example.conveyor.model.PaymentScheduleElement;
import com.example.conveyor.model.ScoringInfo;
import com.example.conveyor.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoringServiceTest {

    private final static LocalDate FIXED_DATE = LocalDate.of(2023, 1, 1);
    private static final BigDecimal BASE_RATE = new BigDecimal("18");
    private static final int AGE = 45;

    @InjectMocks
    private ScoringService service;
    @Mock
    private AppConfig appConfig;

    @Test
    void whenCreateCreditThenSuccess() {
        TimeUtil.useMockTime(FIXED_DATE.atStartOfDay(),ZoneId.systemDefault());
        when(appConfig.getBaseRate()).thenReturn(BASE_RATE);
        ScoringInfo scoringInfo = getScoringInfo();

        CreditInfo credit = service.createCredit(scoringInfo);

        assertThat(credit.getAmount()).isEqualTo(new BigDecimal("300000"));
        assertThat(credit.getTerm()).isEqualTo(6);
        assertThat(credit.getRate()).isEqualTo(new BigDecimal("15"));
        assertThat(credit.getMonthlyPayment()).isEqualTo(new BigDecimal("52210.14"));
        assertThat(credit.getPsk()).isEqualTo(new BigDecimal("313114.34"));

        assertPayments(credit, scoringInfo);
    }

    @Test
    void whenCreateCreditWithWorkExperienceTotalLessThenThrow() {
        when(appConfig.getBaseRate()).thenReturn(BASE_RATE);
        ScoringInfo scoringInfo = getScoringInfo();
        scoringInfo.getEmployment().setWorkExperienceTotal(5);
        scoringInfo.getEmployment().setWorkExperienceCurrent(5);

        assertThatExceptionOfType(ScoringException.class).isThrownBy(() -> service.createCredit(scoringInfo));
    }

    @Test
    void whenCreateCreditWithWorkExperienceCurrentLessThenThrow() {
        when(appConfig.getBaseRate()).thenReturn(BASE_RATE);
        ScoringInfo scoringInfo = getScoringInfo();
        scoringInfo.getEmployment().setWorkExperienceCurrent(1);

        assertThatExceptionOfType(ScoringException.class).isThrownBy(() -> service.createCredit(scoringInfo));
    }

    @Test
    void whenCreateCreditWithSalaryLessThenThrow() {
        when(appConfig.getBaseRate()).thenReturn(BASE_RATE);
        ScoringInfo scoringInfo = getScoringInfo();
        scoringInfo.getEmployment().setSalary(new BigDecimal("10000"));

        assertThatExceptionOfType(ScoringException.class).isThrownBy(() -> service.createCredit(scoringInfo));
    }

    private static void assertPayments(CreditInfo credit, ScoringInfo scoringInfo) {
        List<PaymentScheduleElement> payments = credit.getPaymentSchedule();
        assertThat(payments).hasSize(scoringInfo.getTerm());
        assertPayment(payments, 1, "52210.14", "48388.22", "3821.92", "251611.78");
        assertPayment(payments, 2, "52210.14", "49314.88", "2895.26", "202296.90");
        assertPayment(payments, 3, "52210.14", "49632.93", "2577.21", "152663.97");
        assertPayment(payments, 4, "52210.14", "50327.98", "1882.16", "102335.99");
        assertPayment(payments, 5, "52210.14", "50906.41", "1303.73", "51429.58");
        assertPayment(payments, 6, "52063.64", "51429.58", "634.06", "0.00");
    }

    private static void assertPayment(List<PaymentScheduleElement> payments, int number, String totalPayment,
                                      String debtPayment, String interestPayment, String remainingDebt) {
        assertThat(payments)
                .filteredOn(paymentScheduleElement -> paymentScheduleElement.getNumber() == number)
                .singleElement()
                .satisfies(payment -> {
                            assertThat(payment.getTotalPayment()).isEqualTo(new BigDecimal(totalPayment));
                            assertThat(payment.getDebtPayment()).isEqualTo(new BigDecimal(debtPayment));
                            assertThat(payment.getInterestPayment()).isEqualTo(new BigDecimal(interestPayment));
                            assertThat(payment.getRemainingDebt()).isEqualTo(new BigDecimal(remainingDebt));
                        }
                );
    }

    private static ScoringInfo getScoringInfo() {
        ScoringInfo scoringInfo = new ScoringInfo();
        scoringInfo.setBirthdate(LocalDate.now().minusYears(AGE));
        scoringInfo.setAmount(new BigDecimal("300000"));
        scoringInfo.setTerm(6);
        scoringInfo.setDependentAmount(1);
        scoringInfo.setSalaryClient(true);
        scoringInfo.setInsuranceEnabled(true);
        scoringInfo.setEmployment(getEmployment());
        scoringInfo.setMaritalStatus(ScoringInfo.Marital.MARRIED);
        return scoringInfo;
    }

    private static Employment getEmployment() {
        Employment employment = new Employment();
        employment.setWorkExperienceCurrent(10);
        employment.setWorkExperienceTotal(15);
        employment.setSalary(new BigDecimal("50000"));
        employment.setPosition(Employment.Position.TOP_MANAGER);
        employment.setEmploymentStatus(Employment.EmploymentStatus.BUSINESS_OWNER);
        return employment;
    }
}