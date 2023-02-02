package com.example.deal.service;

import com.example.deal.db.entity.Application;
import com.example.deal.db.entity.Client;
import com.example.deal.db.entity.Credit;
import com.example.deal.db.entity.Passport;
import com.example.deal.db.enums.ApplicationStatus;
import com.example.deal.db.enums.ChangeType;
import com.example.deal.db.enums.CreditStatus;
import com.example.deal.db.repository.ApplicationRepository;
import com.example.deal.db.repository.ClientRepository;
import com.example.deal.db.repository.CreditRepository;
import com.example.deal.dto.request.FinishRegistrationRequestDTO;
import com.example.deal.dto.request.LoanApplicationRequestDTO;
import com.example.deal.dto.response.CreditDTO;
import com.example.deal.dto.response.LoanOfferDTO;
import com.example.deal.integration.conveyor.ConveyorService;
import com.example.deal.mapper.DealMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DealServiceTest {
    @InjectMocks
    private DealService dealService;
    @Mock
    private ConveyorService conveyorService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private CreditRepository creditRepository;
    @Spy
    private DealMapper dealMapper;

    @Test
    void application() {
        LoanApplicationRequestDTO request = createLoanApplicationRequestDTO();
        Client client = new Client();
        UUID id = UUID.randomUUID();
        Application application = createApplication(id);
        List<LoanOfferDTO> offers = createOffers();
        when(clientRepository.save(any())).thenReturn(client);
        when(applicationRepository.save(any())).thenReturn(application);
        when(conveyorService.offers(request)).thenReturn(offers);

        List<LoanOfferDTO> offersResult = dealService.application(request);

        assertThat(offersResult)
                .singleElement()
                .satisfies(offer -> assertThat(offer.getApplicationId()).isEqualTo(id));
    }

    @Test
    void offer() {
        LoanOfferDTO request = createLoanOfferDTO();
        Application application = createApplication(UUID.randomUUID());
        when(applicationRepository.getReferenceById(any())).thenReturn(application);

        dealService.offer(request);

        assertThat(application.getAppliedOffer()).isEqualTo(request);
        assertThat(application.getStatus()).isEqualTo(ApplicationStatus.APPROVED);
        assertHistory(application, ApplicationStatus.APPROVED, ChangeType.MANUAL);
        verify(applicationRepository).save(application);
    }

    @Test
    void calculate() {
        UUID id = UUID.randomUUID();
        Application application = createApplication(UUID.randomUUID());
        when(applicationRepository.getReferenceById(id)).thenReturn(application);
        when(conveyorService.calculation(any())).thenReturn(new CreditDTO());
        when(creditRepository.save(any())).thenReturn(new Credit());

        dealService.calculate(id.toString(), new FinishRegistrationRequestDTO());

        assertHistory(application, ApplicationStatus.CC_APPROVED, ChangeType.AUTOMATIC);
        verify(applicationRepository).save(application);
    }

    private static void assertHistory(Application application, ApplicationStatus status, ChangeType changeType) {
        assertThat(application.getStatusHistory())
                .singleElement().satisfies(statusHistory -> {
                    assertThat(statusHistory.getStatus()).isEqualTo(status);
                    assertThat(statusHistory.getChangeType()).isEqualTo(changeType);
                });
    }

    private static Application createApplication(UUID id) {
        Client client = new Client();
        client.setPassport(new Passport());
        Application application = new Application();
        application.setId(id);
        application.setClient(client);
        application.setAppliedOffer(new LoanOfferDTO());
        return application;
    }

    private static List<LoanOfferDTO> createOffers() {
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        return List.of(loanOfferDTO);
    }

    private static LoanApplicationRequestDTO createLoanApplicationRequestDTO() {
        LoanApplicationRequestDTO request = new LoanApplicationRequestDTO();
        request.setFirstName("Сергей");
        request.setLastName("Ершов");
        request.setMiddleName("Иванович");
        request.setPassportNumber("909021");
        request.setPassportSeries("4012");
        request.setBirthdate(LocalDate.now().minusYears(40));
        request.setEmail("email@ya.ru");
        request.setAmount(new BigDecimal("300000"));
        request.setTerm(6);
        return request;
    }

    private static LoanOfferDTO createLoanOfferDTO() {
        LoanOfferDTO request = new LoanOfferDTO();
        request.setApplicationId(UUID.randomUUID());
        return request;
    }
}