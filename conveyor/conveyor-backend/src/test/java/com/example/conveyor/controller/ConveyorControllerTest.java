package com.example.conveyor.controller;

import com.example.conveyor.ConveyorApplication;
import com.example.conveyor.dto.request.LoanApplicationRequestDTO;
import com.example.conveyor.dto.request.ScoringDataDTO;
import com.example.conveyor.service.ConveyorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ConveyorApplication.class)
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
class ConveyorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConveyorService conveyorService;

    @Test
    void whenCallOffersWithValidRequestThenResponseStatusShouldBe200() throws Exception {
        var request = getRequestFromJson("dto/LoanApplicationRequest.json", LoanApplicationRequestDTO.class);

        mockMvc.perform(post("/conveyor/offers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(conveyorService).offers(request);
    }

    @Test
    void whenCallOffersWithNotValidRequestThenResponseStatusShouldBe500() throws Exception {
        var request = getRequestFromJson("dto/LoanApplicationRequest.json", LoanApplicationRequestDTO.class);
        request.setTerm(1);

        mockMvc.perform(post("/conveyor/offers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(conveyorService, never()).offers(request);
    }

    @Test
    void whenCallCalculationWithValidRequestThenResponseStatusShouldBe200() throws Exception {
        var request = getRequestFromJson("dto/ScoringDataRequest.json", ScoringDataDTO.class);

        mockMvc.perform(post("/conveyor/calculation")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(conveyorService).calculation(request);
    }

    @Test
    void whenCallCalculationWithNotValidRequestThenResponseStatusShouldBe400() throws Exception {
        var request = getRequestFromJson("dto/ScoringDataRequest.json", ScoringDataDTO.class);
        request.setAmount(BigDecimal.ZERO);

        mockMvc.perform(post("/conveyor/calculation")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(conveyorService, never()).calculation(request);
    }

    private <T> T getRequestFromJson(String path, Class<T> clazz) throws IOException {
        var inputStream = new ClassPathResource(path).getInputStream();
        return objectMapper.readValue(inputStream, clazz);
    }
}