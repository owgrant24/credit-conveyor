package com.example.deal.mapper;

import com.example.deal.db.entity.Application;
import com.example.deal.db.entity.Client;
import com.example.deal.db.entity.Credit;
import com.example.deal.db.entity.Passport;
import com.example.deal.dto.request.FinishRegistrationRequestDTO;
import com.example.deal.dto.request.LoanApplicationRequestDTO;
import com.example.deal.dto.request.ScoringDataDTO;
import com.example.deal.dto.response.CreditDTO;
import com.example.deal.dto.response.LoanOfferDTO;
import org.springframework.stereotype.Component;

@Component
public class DealMapper {

    public Client map(LoanApplicationRequestDTO request) {
        Passport passport = new Passport();
        passport.setSeries(request.getPassportSeries());
        passport.setNumber(request.getPassportNumber());

        Client client = new Client();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setMiddleName(request.getMiddleName());
        client.setBirthdate(request.getBirthdate());
        client.setEmail(request.getEmail());
        client.setPassport(passport);

        return client;
    }

    public ScoringDataDTO map(FinishRegistrationRequestDTO request, Application application) {
        Client client = application.getClient();
        LoanOfferDTO appliedOffer = application.getAppliedOffer();

        ScoringDataDTO scoringData = new ScoringDataDTO();
        scoringData.setFirstName(client.getFirstName());
        scoringData.setLastName(client.getLastName());
        scoringData.setMiddleName(client.getMiddleName());
        scoringData.setPassportNumber(client.getPassport().getNumber());
        scoringData.setPassportSeries(client.getPassport().getSeries());
        scoringData.setPassportIssueDate(request.getPassportIssueDate());
        scoringData.setPassportIssueBranch(request.getPassportIssueBranch());
        scoringData.setBirthdate(client.getBirthdate());
        scoringData.setAmount(appliedOffer.getRequestedAmount());
        scoringData.setTerm(appliedOffer.getTerm());
        scoringData.setDependentAmount(request.getDependentAmount());
        scoringData.setIsSalaryClient(appliedOffer.getIsSalaryClient());
        scoringData.setIsInsuranceEnabled(appliedOffer.getIsInsuranceEnabled());
        scoringData.setGender(request.getGender());
        scoringData.setEmployment(request.getEmployment());
        scoringData.setMaritalStatus(request.getMaritalStatus());
        scoringData.setAccount(request.getAccount());
        return scoringData;
    }

    public Credit map(CreditDTO creditDTO) {
        Credit credit = new Credit();
        credit.setAmount(creditDTO.getAmount());
        credit.setTerm(creditDTO.getTerm());
        credit.setRate(creditDTO.getRate());
        credit.setIsSalaryClient(creditDTO.getIsSalaryClient());
        credit.setIsInsuranceEnabled(creditDTO.getIsInsuranceEnabled());
        credit.setMonthlyPayment(creditDTO.getMonthlyPayment());
        credit.setPaymentSchedule(creditDTO.getPaymentSchedule());
        credit.setPsk(creditDTO.getPsk());
        return credit;
    }
}
