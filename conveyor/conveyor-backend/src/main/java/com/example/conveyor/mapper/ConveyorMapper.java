package com.example.conveyor.mapper;

import com.example.conveyor.dto.request.EmploymentDTO;
import com.example.conveyor.dto.request.LoanApplicationRequestDTO;
import com.example.conveyor.dto.request.ScoringDataDTO;
import com.example.conveyor.dto.response.CreditDTO;
import com.example.conveyor.dto.response.LoanOfferDTO;
import com.example.conveyor.dto.response.PaymentScheduleElementDTO;
import com.example.conveyor.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConveyorMapper {

    public LoanInfo mapRequest(LoanApplicationRequestDTO loanApplicationRequest) {
        LoanInfo loanInfo = new LoanInfo();
        loanInfo.setAmount(loanApplicationRequest.getAmount());
        loanInfo.setTerm(loanApplicationRequest.getTerm());
        return loanInfo;
    }

    public List<LoanOfferDTO> mapResponse(List<LoanOffer> loanOffers) {
        return loanOffers.stream()
                .map(this::mapLoanOfferDTO)
                .toList();
    }

    public ScoringInfo mapRequest(ScoringDataDTO request) {
        ScoringInfo scoringInfo = new ScoringInfo();
        scoringInfo.setFirstName(request.getFirstName());
        scoringInfo.setLastName(request.getLastName());
        scoringInfo.setMiddleName(request.getMiddleName());
        scoringInfo.setPassportNumber(request.getPassportNumber());
        scoringInfo.setPassportSeries(request.getPassportSeries());
        scoringInfo.setPassportIssueDate(request.getPassportIssueDate());
        scoringInfo.setPassportIssueBranch(request.getPassportIssueBranch());
        scoringInfo.setBirthdate(request.getBirthdate());
        scoringInfo.setAmount(request.getAmount());
        scoringInfo.setTerm(request.getTerm());
        scoringInfo.setDependentAmount(request.getDependentAmount());
        scoringInfo.setSalaryClient(request.getIsSalaryClient());
        scoringInfo.setInsuranceEnabled(request.getIsInsuranceEnabled());
        scoringInfo.setGender(mapGender(request.getGender()));
        scoringInfo.setEmployment(mapEmployment(request.getEmployment()));
        scoringInfo.setMaritalStatus(mapMarital(request.getMaritalStatus()));
        scoringInfo.setAccount(request.getAccount());
        return scoringInfo;
    }

    public CreditDTO mapResponse(CreditInfo creditInfo) {
        CreditDTO response = new CreditDTO();
        response.setAmount(creditInfo.getAmount());
        response.setTerm(creditInfo.getTerm());
        response.setRate(creditInfo.getRate());
        response.setIsSalaryClient(creditInfo.isSalaryClient());
        response.setIsInsuranceEnabled(creditInfo.isInsuranceEnabled());
        response.setMonthlyPayment(creditInfo.getMonthlyPayment());
        response.setPaymentSchedule(mapPaymentScheduleDTO(creditInfo.getPaymentSchedule()));
        response.setPsk(creditInfo.getPsk());
        return response;
    }

    private LoanOfferDTO mapLoanOfferDTO(LoanOffer loanOffer) {
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        loanOfferDTO.setApplicationId(loanOffer.getApplicationId());
        loanOfferDTO.setTotalAmount(loanOffer.getTotalAmount());
        loanOfferDTO.setIsSalaryClient(loanOffer.isSalaryClient());
        loanOfferDTO.setMonthlyPayment(loanOffer.getMonthlyPayment());
        loanOfferDTO.setRate(loanOffer.getRate());
        loanOfferDTO.setRequestedAmount(loanOffer.getRequestedAmount());
        loanOfferDTO.setTerm(loanOffer.getTerm());
        loanOfferDTO.setIsInsuranceEnabled(loanOffer.isInsuranceEnabled());
        return loanOfferDTO;
    }

    private ScoringInfo.Gender mapGender(ScoringDataDTO.GenderDTO genderDTO) {
        return switch (genderDTO) {
            case MALE -> ScoringInfo.Gender.MALE;
            case FEMALE -> ScoringInfo.Gender.FEMALE;
            case NON_BINARY -> ScoringInfo.Gender.NON_BINARY;
        };
    }

    private Employment.Position mapPosition(EmploymentDTO.PositionDTO positionDTO) {
        return switch (positionDTO) {
            case WORKER -> Employment.Position.WORKER;
            case MID_MANAGER -> Employment.Position.MID_MANAGER;
            case TOP_MANAGER -> Employment.Position.TOP_MANAGER;
            case OWNER -> Employment.Position.OWNER;
        };
    }

    private Employment.EmploymentStatus mapEmploymentStatus(EmploymentDTO.EmploymentStatusDTO employmentStatusDTO) {
        return switch (employmentStatusDTO) {
            case EMPLOYED -> Employment.EmploymentStatus.EMPLOYED;
            case UNEMPLOYED -> Employment.EmploymentStatus.UNEMPLOYED;
            case SELF_EMPLOYED -> Employment.EmploymentStatus.SELF_EMPLOYED;
            case BUSINESS_OWNER -> Employment.EmploymentStatus.BUSINESS_OWNER;
        };
    }

    private Employment mapEmployment(EmploymentDTO employmentDTO) {
        Employment employment = new Employment();
        employment.setWorkExperienceCurrent(employmentDTO.getWorkExperienceCurrent());
        employment.setPosition(mapPosition(employmentDTO.getPosition()));
        employment.setWorkExperienceTotal(employmentDTO.getWorkExperienceTotal());
        employment.setEmploymentStatus(mapEmploymentStatus(employmentDTO.getEmploymentStatus()));
        employment.setSalary(employmentDTO.getSalary());
        return employment;
    }

    private ScoringInfo.Marital mapMarital(ScoringDataDTO.MaritalDTO maritalDTO) {
        return switch (maritalDTO) {
            case SINGLE -> ScoringInfo.Marital.SINGLE;
            case DIVORCED -> ScoringInfo.Marital.DIVORCED;
            case WIDOW_WIDOWER -> ScoringInfo.Marital.WIDOW_WIDOWER;
            case MARRIED -> ScoringInfo.Marital.MARRIED;
            case REMARRIED -> ScoringInfo.Marital.REMARRIED;
        };
    }

    private List<PaymentScheduleElementDTO> mapPaymentScheduleDTO(List<PaymentScheduleElement> list) {
        return list.stream()
                .map(this::mapPaymentDTO)
                .toList();
    }

    private PaymentScheduleElementDTO mapPaymentDTO(PaymentScheduleElement payment) {
        PaymentScheduleElementDTO paymentDTO = new PaymentScheduleElementDTO();
        paymentDTO.setNumber(payment.getNumber());
        paymentDTO.setDate(payment.getDate());
        paymentDTO.setTotalPayment(payment.getTotalPayment());
        paymentDTO.setInterestPayment(payment.getInterestPayment());
        paymentDTO.setDebtPayment(payment.getDebtPayment());
        paymentDTO.setRemainingDebt(payment.getRemainingDebt());
        return paymentDTO;
    }
}
