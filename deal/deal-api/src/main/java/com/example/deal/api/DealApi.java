package com.example.deal.api;

import com.example.deal.dto.request.FinishRegistrationRequestDTO;
import com.example.deal.dto.request.LoanApplicationRequestDTO;
import com.example.deal.dto.response.CreditDTO;
import com.example.deal.dto.response.LoanOfferDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "DealApi", description = "Операции с проведением сделок")
public interface DealApi {

    @PostMapping(path = "/deal/application", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Рассчитать возможные условия кредита", summary = "Расчёт возможных условий кредита")
    @ApiResponse(responseCode = "200", description = "OK", content =
    @Content(schema = @Schema(implementation = LoanOfferDTO.class)))
    List<LoanOfferDTO> application(@Parameter(description = "Тело запроса", required = true)
                                   @RequestBody @Valid LoanApplicationRequestDTO loanApplicationRequestDTO,
                                   @RequestHeader HttpHeaders headers);

    @PutMapping(path = "/deal/offer", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Выбрать предложение", summary = "Выбор предложения")
    @ApiResponse(responseCode = "200", description = "OK")
    void offer(@Parameter(description = "Тело запроса", required = true)
               @RequestBody @Valid LoanOfferDTO loanOfferDTO,
               @RequestHeader HttpHeaders headers);

    @PutMapping(path = "/deal/calculate/{applicationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            description = "Завершить регистрацию + полностью рассчитать параметры кредита",
            summary = "Завершение регистрации + Полный расчет параметров кредита"
    )
    @ApiResponse(responseCode = "200", description = "OK")
    void calculate(@PathVariable("applicationId") String applicationId,
                   @Parameter(description = "Тело запроса", required = true)
                   @RequestBody @Valid FinishRegistrationRequestDTO finishRegistrationRequestDTO,
                   @RequestHeader HttpHeaders headers);
}
